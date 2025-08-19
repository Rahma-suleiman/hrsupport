package hm.project.hrsupport.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import hm.project.hrsupport.enums.AttendanceStatusEnum;
import lombok.Data;

@Data
// public class AttendDTO {

// @JsonProperty(access = JsonProperty.Access.READ_ONLY)
// private Long id;

// // private Long id;
// @JsonFormat(pattern = "yyyy-MM-dd")
// private LocalDate date;

// @JsonFormat(pattern = "HH:mm") // <-- formats time as "08:55"
// private LocalTime checkInTime;

// @JsonFormat(pattern = "HH:mm") // <-- formats time as "17:05"
// private LocalTime checkOutTime;

// private AttendanceStatusEnum status; // Only enum values allowed

// @JsonProperty("employee_id") // <-- match incoming JSON field
// private Long employeeId;
// }

public class AttendDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    private AttendanceStatusEnum status;

    @JsonProperty("employee_id")
    private Long employeeId;

    // Customize JSON output for checkInTime
    // @JsonInclude(JsonInclude.Include.NON_NULL) => It tells Spring Boot / Jackson
    // to exclude this field from the JSON output if the value is null.In other
    // words: if this method returns
    // null, the checkInTime field will not appear in the JSON response.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "HH:mm") // ensure HH:mm format
    // This is a getter method for the checkInTime field in your DTO.
    // Spring / Jackson calls this method when converting the object to JSON.
    public LocalTime getCheckInTime() {
        if (status == AttendanceStatusEnum.ABSENT || status == AttendanceStatusEnum.ON_LEAVE) {
            return null; // If the employee is absent/on-leave, the getter returns null. Because of
                         // @JsonInclude, Jackson will omit checkInTime from the JSON.
        }
        return checkInTime; // If the status is PRESENT or LATE, it returns the actual checkInTime. The JSON
                            // output will include this field as normal.
    }

    // Customize JSON output for checkOutTime
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "HH:mm") // ensure HH:mm format
    public LocalTime getCheckOutTime() {
        if (status == AttendanceStatusEnum.ABSENT || status == AttendanceStatusEnum.ON_LEAVE) {
            return null;
        }
        return checkOutTime;
    }
}

// ✅ Behavior

// status = ABSENT or ON_LEAVE → checkInTime and checkOutTime won’t appear in
// JSON.

// status = PRESENT or LATE → all fields included.
