package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.AttendDTO;
import hm.project.hrsupport.entity.Attendance;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.exception.ApiRequestException;
// import hm.project.hrsupport.enums.AttendanceStatusEnum;
// import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.AttendRepository;
import hm.project.hrsupport.repository.EmpRepository;

@Service
public class AttendService {
    
    @Autowired
    private AttendRepository attendRepository;
    private ModelMapper modelMapper;
    private EmpRepository empRepository;

    public AttendService(AttendRepository attendRepository, ModelMapper modelMapper, EmpRepository empRepository) {
        this.attendRepository = attendRepository;
        this.modelMapper = modelMapper;
        this.empRepository = empRepository;
    }


    // FOR TESTING EXCEPTION HANDLING   
    // public List<AttendDTO> getAllAttendance() {
    //     throw new ApiRequestException("Oopscannot get all attendance with custom exception");
    //     // throw new IllegalStateException("Oopscannot get all attendance");
    // }
    public List<AttendDTO> getAllAttendance() {
        List<Attendance> attendance = attendRepository.findAll();
        return attendance.stream()
                .map(attend -> modelMapper.map(attend, AttendDTO.class))
                .collect(Collectors.toList());
    }


    public AttendDTO addAttendance(AttendDTO attendDTO) {
        Attendance attend = modelMapper.map(attendDTO, Attendance.class);

        Employee emp = empRepository.findById(attendDTO.getEmployeeId())
                .orElseThrow(()-> new IllegalStateException("Employee ID not found"));
        attend.setEmployee(emp);
        Attendance saveAttendance = attendRepository.save(attend);
        return modelMapper.map(saveAttendance, AttendDTO.class);
    }
    // // set manually the checkIn and Out time for absent and on leave employee
    // public AttendDTO addAttendance(AttendDTO attendDTO) {
    //     Attendance attend = modelMapper.map(attendDTO, Attendance.class);
    //     Employee emp = empRepository.findById(attendDTO.getEmployeeId())
    //             .orElseThrow(()-> new IllegalStateException("Employee ID not found"));
    //     attend.setEmployee(emp);
    //     Attendance saveAttendance = attendRepository.save(attend);
    //     AttendDTO resultDTO = modelMapper.map(saveAttendance, AttendDTO.class);
    //     if (saveAttendance.getStatus() == AttendanceStatusEnum.ABSENT || saveAttendance.getStatus() == AttendanceStatusEnum.ON_LEAVE) {
    //         // Only keep date, status, and employeeId
    //         resultDTO.setCheckInTime(null);
    //         resultDTO.setCheckInTime(null);
    //     }
    //     return resultDTO;
    // }


    public AttendDTO getAttendanceById(Long id) {
        Attendance attend = attendRepository.findById(id)
                    .orElseThrow(()-> new ApiRequestException("attendance not found with id "+id));
        return modelMapper.map(attend, AttendDTO.class);
    }


    public void deleteAttendance(Long id) {
        attendRepository.deleteById(id);
    }


    // public AttendDTO editAttendance(String id, AttendDTO attendDTO) {
    //     Attendance attendance = attendRepository.findById(id)
    //             .orElseThrow(()-> new ApiRequestException("Attendance not found"))
    //     modelMapper.map(attendDTO, attendance);
    //     Employee empAttend = 
    // }

}
// {
//     "date": "2025-08-19",
//     "checkInTime": "08:30",
//     "checkOutTime": "17:00",
//     "status": "PRESENT",
//     "employeeId": 1
// }