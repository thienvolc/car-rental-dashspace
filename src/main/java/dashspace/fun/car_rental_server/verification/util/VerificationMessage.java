package dashspace.fun.car_rental_server.verification.util;

import dashspace.fun.car_rental_server.verification.enums.OtpVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum VerificationMessage {
    PENDING(OtpVerificationStatus.PENDING, "verification.pending"),
    VERIFIED(OtpVerificationStatus.VERIFIED, "verification.verified"),
    FAILED(OtpVerificationStatus.FAILED, "verification.failed"),
    EXPIRED(OtpVerificationStatus.EXPIRED, "verification.expired"),
    CANCELLED(OtpVerificationStatus.CANCELLED, "verification.cancelled");

    private final OtpVerificationStatus status;
    private final String message;

    private static final Map<OtpVerificationStatus, String> messageRegistry =
            Arrays.stream(values())
                    .collect(Collectors.toMap(VerificationMessage::getStatus,
                            VerificationMessage::getMessage));

    public static String fromStatus(OtpVerificationStatus status) {
        return messageRegistry.get(status);
    }
}
