package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.PayrollDTO;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.entity.Payroll;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.EmpRepository;
import hm.project.hrsupport.repository.PayrollRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor //use this to automaticall inject constructor or use @Autowired
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final ModelMapper modelMapper;
    private final EmpRepository empRepository;

    public List<PayrollDTO> getAllPayroll() {
        List<Payroll> payroll = payrollRepository.findAll();
        return payroll.stream()
            .map(pay-> modelMapper.map(payroll, PayrollDTO.class))
            .collect(Collectors.toList());
    }

    public PayrollDTO addPayroll(PayrollDTO payrollDTO) {
        Payroll payroll = modelMapper.map(payrollDTO, Payroll.class);

        Employee empId = empRepository.findById(payrollDTO.getEmployeeId())
                    .orElseThrow(()-> new ApiRequestException("Oops cannot get payroll with id" + payrollDTO.getEmployeeId()));
        payroll.setEmployee(empId);
        // Net Salary=Salary+Bonus−Deductions
        int netSalary = payrollDTO.getSalary()+payrollDTO.getBonus()-payrollDTO.getDeduction();
        // set the calculated net salar
        payroll.setNetSalary(netSalary);

        Payroll savingPayroll = payrollRepository.save(payroll);
        
        return modelMapper.map(savingPayroll, PayrollDTO.class);
    }

}
