package hm.project.hrsupport.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmpDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dob;
    private LocalDate hireDate;
    private String position; // manager, CEO, software Developer
    private Integer salary;
    private String status; // Active, On Leave, Resigned

    // private EmpDTO manager;

    private Long departmentId;

}
