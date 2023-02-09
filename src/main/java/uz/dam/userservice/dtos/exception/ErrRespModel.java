package uz.dam.userservice.dtos.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import uz.dam.userservice.dtos.DeveloperMessage;
import uz.dam.userservice.exceptions.enums.ExceptionType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrRespModel {

    DeveloperMessage message;
    ExceptionType type;
    HttpStatus status;
    String entityName;
    String detail;
    LocalDateTime timestamp;



}
