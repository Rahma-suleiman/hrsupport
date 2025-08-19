package hm.project.hrsupport.entity;

// import java.util.ArrayList;
// import java.util.List;

// import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
// import jakarta.persistence.*;
// import lombok.Data;
// import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// @Data
@Getter
@Setter
// @EqualsAndHashCode(callSuper = false)
@Table(name = "department")
@Entity
public class Department extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Employee> employee;

    //with this "new ArrayList<>()" means you can create list of emploees empty (no NullPointerException).
    // @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Employee> employees = new ArrayList<>();

}
