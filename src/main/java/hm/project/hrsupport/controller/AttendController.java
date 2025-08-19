package hm.project.hrsupport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.dto.AttendDTO;
import hm.project.hrsupport.service.AttendService;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v2/hrsupport/attendance")
public class AttendController {
    @Autowired
    private AttendService attendService;

    @GetMapping
    public ResponseEntity<List<AttendDTO>> getAllAttendance() {
        List<AttendDTO> attendance = attendService.getAllAttendance();
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<AttendDTO> addAttendance(@RequestBody AttendDTO attendDTO) {
        AttendDTO attendance = attendService.addAttendance(attendDTO);
        return new ResponseEntity<>(attendance, HttpStatus.CREATED);
    }
    
}
