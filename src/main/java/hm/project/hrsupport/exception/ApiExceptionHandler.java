package hm.project.hrsupport.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        // 1. Create payload obj containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                // e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
            );
        // 2. Return response entity
        return new ResponseEntity<>(apiException,badRequest);

    }
//     @RestControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(HttpMessageNotReadableException.class)
//     public ResponseEntity<String> handleEnumError(HttpMessageNotReadableException ex) {
//         if (ex.getMessage().contains("AttendanceStatus")) {
//             return ResponseEntity.badRequest().body(
//                 "Invalid status value. Allowed values: PRESENT, ABSENT, LATE, ON_LEAVE"
//             );
//         }
//         return ResponseEntity.badRequest().body("Invalid request data.");
//     }
// }

}
// package hm.project.hrsupport.exception;

// import java.time.ZoneId;
// import java.time.ZonedDateTime;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// public class ApiExceptionHandler {
//     public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
//         // 1. Create payload obj containing exception details
//         ApiException apiException = new ApiExceptionHandler(
//                 e.getMessage(),
//                 e,
//                 badRequest,
//                 // HttpStatus.BAD_REQUEST,
//                 ZonedDateTime.now(ZoneId.of("Z"))
//             );
//         // 2. Return response entity
//         // return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);

//     }
// }
