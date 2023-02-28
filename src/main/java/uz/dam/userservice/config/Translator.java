package uz.dam.userservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

/**
 * Translator for Exception messages
 */
@Component
public class Translator {

    @Value(value = "${spring.application.translation.properties.defaultLocale}")
    public static final String defaultLocale = "uz";
    private static ResourceBundleMessageSource messageSource;
 
    public Translator(@Qualifier("messageResourceBundleSource") ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * get message from resource bundle message properties
     * @param code properties code for get message
     * @return return translated message
     */
    public static String getMessage(String code) {
        try {
            HttpServletRequest request =(HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST);
            Locale locale = new Locale(Objects.requireNonNullElse(request.getHeader("lang"), defaultLocale));
            return messageSource.getMessage(code, null, locale);
        }catch (Exception e){
            e.printStackTrace();
        }
        return messageSource.getMessage(code, null, new Locale(defaultLocale));
    }
}