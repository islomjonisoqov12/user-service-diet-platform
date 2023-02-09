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
    private DeveloperMessage developerMessage;
    private ExceptionType type;
    private String entityName;


    public ValidationException(DeveloperMessage message, ExceptionType type, String entityName) {
        super(message.getUz());
        this.timestamp = new Timestamp(new Date().getTime());
        this.developerMessage = message;
        this.type = type;
        this.entityName = entityName;
    }
    public ValidationException(DeveloperMessage message,ExceptionType type, String entityName, Throwable cause) {
        super(message.getUz(),cause);
        this.timestamp = new Timestamp(new Date().getTime());
        this.developerMessage = message;
        this.type = type;
        this.entityName = entityName;
    }
}
