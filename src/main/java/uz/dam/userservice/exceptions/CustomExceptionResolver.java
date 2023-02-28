package uz.dam.userservice.exceptions;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import uz.dam.userservice.exceptions.enums.ExceptionType;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if(ex instanceof BadRequestException){
            BadRequestException bad = (BadRequestException) ex;
            return errorBuilder(ex, env, ErrorType.BAD_REQUEST, bad.getType(), bad.getEntityName());
        } else if(ex instanceof ConstraintViolationException){
            ConstraintViolationException cVE = (ConstraintViolationException) ex;
            cVE.getConstraintViolations();
            return errorBuilder(ex, env, ErrorType.BAD_REQUEST, null, "validate");
        } else if(ex instanceof AccessDeniedException){
            return errorBuilder(ex, env, ErrorType.FORBIDDEN, ExceptionType.ACCESS_DENIED, "not");
        }else {
            return errorBuilder(ex, env, ErrorType.INTERNAL_ERROR, null, "not");
        }
    }

    private static GraphQLError errorBuilder(Throwable ex, DataFetchingEnvironment env, ErrorType errorType, ExceptionType enumType, String entityName) {
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(Map.of("type", enumType==null?"":enumType, "entity", entityName==null? "":entityName))
                .build();
    }

}