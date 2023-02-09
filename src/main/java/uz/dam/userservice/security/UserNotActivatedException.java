package uz.dam.userservice.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;
import uz.dam.userservice.dtos.DeveloperMessage;


public class UserNotActivatedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;
    private DeveloperMessage developerMessage;

    public DeveloperMessage getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(DeveloperMessage developerMessage) {
        this.developerMessage = developerMessage;
    }

    public UserNotActivatedException(DeveloperMessage message) {
        super(message.toJson());
    }

    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
