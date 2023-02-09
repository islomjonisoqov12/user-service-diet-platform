package uz.dam.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BadRequestAlertException{

    public ResourceNotFoundException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }

    public ResourceNotFoundException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, entityName, errorKey);
    }
}
