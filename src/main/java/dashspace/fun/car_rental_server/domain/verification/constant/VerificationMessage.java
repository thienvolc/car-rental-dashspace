package dashspace.fun.car_rental_server.domain.verification.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VerificationMessage {

    PENDING("verification.pending"),
    VERIFIED("verification.verified"),
    FAILED("verification.failed"),
    EXPIRED("verification.expired"),
    CANCELLED("verification.cancelled")
    ;

    private final String message;
}
