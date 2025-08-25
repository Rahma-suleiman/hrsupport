package hm.project.hrsupport.dto;

import java.time.LocalDate;
import java.util.List;

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

    // FK
    private Long managerId; // Reference manager by ID only

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //  Only visible in response
    private List<Long> subordinateIds; // Optional (only if you want manager -> list of employees)
    
    private Long departmentId;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
    private List<Long> reviewId;


}
