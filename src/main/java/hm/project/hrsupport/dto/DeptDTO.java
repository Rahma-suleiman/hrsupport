package hm.project.hrsupport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// import java.util.List;

import lombok.Data;

@Data
public class DeptDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;

    // no need for this field in dto
    // private List<EmpDTO> employees;

}
