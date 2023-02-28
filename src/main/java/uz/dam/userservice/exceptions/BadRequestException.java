package uz.dam.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.dam.userservice.exceptions.enums.ExceptionType;
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends ValidationException{

    public BadRequestException(String message, ExceptionType type, String entityName) {
        super(message, type, entityName);
    }

    public BadRequestException(String message, ExceptionType type, String entityName, Throwable cause) {
        super(message, type, entityName, cause);
    }
}
