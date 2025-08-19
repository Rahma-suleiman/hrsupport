package hm.project.hrsupport.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import hm.project.hrsupport.enums.PaymentStatusEnum;
import lombok.Data;

@Data
public class PayrollDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @JsonFormat(pattern = "yyyy-MM") 
  private LocalDate month;
  private Integer salary;
  private Integer bonus;
  private Integer deduction;
  
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer netSalary;

  private PaymentStatusEnum paymentStatus;

  // FK for employee
  private Long employeeId;
}
