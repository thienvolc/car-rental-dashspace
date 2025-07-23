package dashspace.fun.car_rental_server.exception.handler;

import dashspace.fun.car_rental_server.common.util.I18nUtils;
import dashspace.fun.car_rental_server.exception.BusinessException;
import dashspace.fun.car_rental_server.exception.handler.ErrorResponse.ValidationError;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static dashspace.fun.car_rental_server.exception.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(BusinessException ex) {
        ErrorResponse response = ErrorResponse.fromCodeAndMessage(
                ex.getErrorCode().getCode(), ex.getMessage());

        log.info("Business Exception: {}", response);
        log.debug(ex.getMessage(), ex);

        return new ResponseEntity<>(response, ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(
            MethodArgumentNotValidException ex) {

        List<ValidationError> errors = getAllValidationErrors(ex);
        return ResponseEntity.badRequest()
                .body(ErrorResponse.fromValidationErrors(errors));
    }

    private List<ValidationError> getAllValidationErrors(
            MethodArgumentNotValidException ex) {

        return ex.getBindingResult().getFieldErrors().stream()
                .map(e -> new ValidationError(e.getField(), e.getCode(),
                        I18nUtils.get(e.getDefaultMessage())))
                .toList();
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ErrorResponse.fromErrorCode(BAD_CREDENTIALS));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleException(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(FORBIDDEN)
                .body(ErrorResponse.fromErrorCode(ACCESS_DENIED));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse.fromCodeAndMessage("TBD", ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse.fromErrorCode(USERNAME_NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.fromErrorCode(INTERNAL_EXCEPTION));
    }
}
