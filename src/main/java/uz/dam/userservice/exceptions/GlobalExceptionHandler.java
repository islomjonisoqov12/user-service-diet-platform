package uz.dam.userservice.exceptions;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import uz.dam.userservice.dtos.exception.ErrRespModel;


import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";

//    @Value("${jhipster.clientApp.name}")
//    private String applicationName;

//    @ExceptionHandler(BadRequestAlertException.class)
//    public ResponseEntity<ErrRespModel> handleBadRequestAlertException(BadRequestAlertException exception) {
//        log.error(exception.getMessage(), exception);
//
//        return ResponseEntity.badRequest()
//                .body(ErrRespModel.builder()
//                        .status(HttpStatus.BAD_REQUEST)
//                        .detail(exception.getMessage())
//                        .entityName(exception.getEntityName())
//                        .errorKey(exception.getErrorKey())
//                        .timestamp(LocalDateTime.now())
//                        .build());
//    }

    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<ErrRespModel> handleConnectionError(Exception ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.badRequest()
                .body(ErrRespModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .detail(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleInvalidPasswordException(
            InvalidPasswordException ex, NativeWebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        return create(new InvalidPasswordException(), request);
    }

//    @ExceptionHandler
//    public ResponseEntity<Problem> handleApplicationNotFound(SearchAppNotFound ex, NativeWebRequest request) {
//        log.error(ex.getMessage(), ex);
//        return create(new SearchAppNotFound(ex.getMessage(), ex.getEntityName(),ex.getErrorKey()), request);
//    }

//    @ExceptionHandler
//    public ResponseEntity<Problem> handleEmailAlreadyUsedException(
//            EmailAlreadyUsedException ex, NativeWebRequest request
//    ) {
//        log.error(ex.getMessage(), ex);
//        EmailAlreadyUsedException problem = new EmailAlreadyUsedException();
//        return create(
//                problem,
//                request,
//                HeaderUtil.createFailureAlert(applicationName,
//                        false, problem.getEntityName(), problem.getErrorKey(), problem.getMessage())
//        );
//    }

//    @ExceptionHandler
//    public ResponseEntity<Problem> handleLoginAlreadyUsedException(
//            LoginAlreadyUsedException ex, NativeWebRequest request
//    ) {
//        log.error(ex.getMessage(), ex);
//        LoginAlreadyUsedException problem = new LoginAlreadyUsedException();
//        return create(
//                problem,
//                request,
//                HeaderUtil.createFailureAlert(applicationName,
//                        false, problem.getEntityName(), problem.getErrorKey(), problem.getMessage())
//        );
//    }

//    @ExceptionHandler
//    public ResponseEntity<Problem> handleUsernameAlreadyUsedException(
//            UsernameAlreadyUsedException ex,
//            NativeWebRequest request
//    ) {
//        log.error(ex.getMessage(), ex);
//        LoginAlreadyUsedException problem = new LoginAlreadyUsedException();
//        return create(
//                problem,
//                request,
//                HeaderUtil.createFailureAlert(applicationName, false,
//                        problem.getEntityName(), problem.getErrorKey(), problem.getMessage())
//        );
//    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVM> fieldErrors = result
                .getFieldErrors()
                .stream()
                .map(f ->
                        new FieldErrorVM(
                                f.getObjectName().replaceFirst("DTO$", ""),
                                f.getField(),
                                StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()
                        )
                )
                .collect(Collectors.toList());

        Problem problem = Problem
                .builder()
                .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
                .withTitle("Method argument not valid")
                .withStatus(defaultConstraintViolationStatus())
                .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION)
                .with(FIELD_ERRORS_KEY, fieldErrors)
                .build();
        return create(ex, problem, request);
    }
}
