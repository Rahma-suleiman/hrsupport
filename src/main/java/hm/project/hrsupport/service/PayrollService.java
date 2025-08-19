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
@AllArgsConstructor // use this to automaticall inject constructor or use @Autowired
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final ModelMapper modelMapper;
    private final EmpRepository empRepository;

    public List<PayrollDTO> getAllPayroll() {
        List<Payroll> payroll = payrollRepository.findAll();
        return payroll.stream()
                .map(pay -> modelMapper.map(payroll, PayrollDTO.class))
                .collect(Collectors.toList());
    }

    public PayrollDTO addPayroll(PayrollDTO payrollDTO) {

        // Fetch the employee first
        Employee employee = empRepository.findById(payrollDTO.getEmployeeId())
                .orElseThrow(() -> new ApiRequestException(
                        "Cannot get payroll for employee id " + payrollDTO.getEmployeeId()));

        // Map DTO to entity
        Payroll payroll = modelMapper.map(payrollDTO, Payroll.class);

        // Set the employee
        payroll.setEmployee(employee);
        // Net Salary=Salary+Bonus−Deductions
        // Use employee's salary to calculate netSalary
        int netSalary = employee.getSalary()
                + (payrollDTO.getBonus() != null ? payrollDTO.getBonus() : 0)
                - (payrollDTO.getDeduction() != null ? payrollDTO.getDeduction() : 0);
        // set the calculated net salar
        payroll.setNetSalary(netSalary);

        payroll.setSalary(employee.getSalary());

        Payroll savedPayroll = payrollRepository.save(payroll);

        PayrollDTO savedPayrollDTO = modelMapper.map(savedPayroll, PayrollDTO.class);

        // Set salary in the DTO so it is included in the response.
        // This field is read-only in the DTO and comes from the Employee entity, not
        // from user input.
        savedPayrollDTO.setSalary(savedPayroll.getSalary());

        return savedPayrollDTO;
    }

}
