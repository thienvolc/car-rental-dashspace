package dashspace.fun.car_rental_server.exception.handler;

import dashspace.fun.car_rental_server.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private List<ValidationError> validationErrors;

    public static ErrorResponse fromErrorCode(ErrorCode errorCode) {
        return fromCodeAndMessage(errorCode.getCode(), errorCode.getDefaultMessage());
    }

    public static ErrorResponse fromCodeAndMessage(String code, String message) {
        return new ErrorResponse(code, message, null);
    }

    public static ErrorResponse fromValidationErrors(List<ValidationError> errors) {
        return new ErrorResponse(null, null, errors);
    }

    @Getter
    @AllArgsConstructor
    public static class ValidationError {
        private String field;
        private String code;
        private String message;
    }
}
