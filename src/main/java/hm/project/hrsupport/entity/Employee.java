package hm.project.hrsupport.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
// import lombok.Data;
// import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// @Data
@Getter
@Setter
@Entity
// @EqualsAndHashCode(callSuper = false)
@Table(name = "employee")
public class Employee extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // // Many employees can have the same manager
    // @ManyToOne
    // @JoinColumn(name="manager_id") //FK column
    // private Employee manager; //self referencing (each emp can hv anada emp as
    // their manager)

    // foreign keys
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;
}
