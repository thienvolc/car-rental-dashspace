package dashspace.fun.car_rental_server.exception;

import dashspace.fun.car_rental_server.common.util.I18nUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TOKEN_MISSING("ERR_TOKEN_MISSING", "token.missing", UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS("ERR_EMAIL_ALREADY_EXISTS", "email.already_exists", CONFLICT),
    EMAIL_NOT_VERIFIED("ERR_EMAIL_NOT_VERIFIED", "email.not_verified", BAD_REQUEST),
    BAD_CREDENTIALS("ERR_BAD_CREDENTIALS", "bad_credentials", UNAUTHORIZED),
    USERNAME_NOT_FOUND("ERR_USERNAME_NOT_FOUND", "username.not_found", NOT_FOUND),
    ACCESS_DENIED("ERR_ACCESS_DENIED", "access_denied", FORBIDDEN),
    INTERNAL_EXCEPTION("ERR_INTERNAL_EXCEPTION", "internal_server_error",
            INTERNAL_SERVER_ERROR),
    OTP_EXPIRED("ERR_OTP_EXPIRED", "otp.expired", BAD_REQUEST),
    OTP_NOT_VERIFIED("ERR_OTP_NOT_VERIFIED", "otp.not_verified", BAD_REQUEST),
    PASSWORD_SAME_AS_OLD("ERR_PASSWORD_SAME_AS_OLD", "password.update.same_as_old",
            BAD_REQUEST),
    EXISTING_USER_NOT_FOUND("ERR_EXISTING_USER_NOT_FOUND", "user.not_found", NOT_FOUND),
    IMAGE_UPLOAD_FAILED("ERR_IMAGE_UPLOAD_FAILED", "image.upload.failed",
            INTERNAL_SERVER_ERROR),
    LICENSE_PLATE_ALREADY_EXISTS("ERR_LICENSE_PLATE_ALREADY_EXISTS",
            "license.plate.already_exists", CONFLICT),
    VEHICLE_ALREADY_APPROVED("ERR_VEHICLE_ALREADY_APPROVED", "vehicle.already_approved",
            BAD_REQUEST),
    VEHICLE_ALREADY_REJECTED("ERR_VEHICLE_ALREADY_REJECTED", "vehicle.already_rejected",
            BAD_REQUEST)

    ;

    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

    public String getDefaultMessage() {
        return I18nUtils.get(defaultMessage);
    }
}
