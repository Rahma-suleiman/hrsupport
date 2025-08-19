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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payroll")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(name = "month", columnDefinition = "VARCHAR(7)")
    // private YearMonth month; // stores year + month (e.g., 2024-08)

    private Integer year; // e.g., 2025
    private Integer month; // e.g., 8

    private Integer salary;
    // private Integer bonus;
    // private Integer deduction;
    // @Column(nullable = false)
    private Integer bonus = 0;

    // @Column(nullable = false)
    private Integer deduction = 0;
    private Integer netSalary;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    // FK for employeeId
    @OneToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

}
