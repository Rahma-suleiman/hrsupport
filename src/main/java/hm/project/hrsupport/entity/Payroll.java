package hm.project.hrsupport.entity;

// import java.time.LocalDate;
import java.time.YearMonth;

import hm.project.hrsupport.enums.PaymentStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "payroll")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "month", columnDefinition = "VARCHAR(7)") // <- here
    private YearMonth month; // stores year + month (e.g., 2024-08)
    // private YearMonth month;
    private Integer salary;
    private Integer bonus;
    private Integer deduction;
    private Integer netSalary;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    // FK for employeeId
    @OneToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

}
