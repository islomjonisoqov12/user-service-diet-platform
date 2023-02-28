package uz.dam.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import uz.dam.userservice.dtos.DeveloperMessage;
import uz.dam.userservice.exceptions.enums.ExceptionType;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public  class ValidationException  extends RuntimeException  {

    private Timestamp timestamp;
    private ExceptionType type;
    private String entityName;


    public ValidationException(String message, ExceptionType type, String entityName) {
        super(message);
        this.timestamp = new Timestamp(new Date().getTime());
        this.type = type;
        this.entityName = entityName;
    }
    public ValidationException(String message,ExceptionType type, String entityName, Throwable cause) {
        super(message,cause);
        this.timestamp = new Timestamp(new Date().getTime());
        this.type = type;
        this.entityName = entityName;
    }
}
