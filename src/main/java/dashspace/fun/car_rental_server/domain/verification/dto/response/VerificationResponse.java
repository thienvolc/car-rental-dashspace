package dashspace.fun.car_rental_server.domain.verification.dto.response;

import dashspace.fun.car_rental_server.domain.verification.constant.VerificationPurpose;
import dashspace.fun.car_rental_server.domain.verification.constant.VerificationStatus;
import lombok.Builder;

@Builder
public record VerificationResponse(
        Integer id,
        VerificationStatus status,
        VerificationPurpose purpose,
        String email,
        String message,
        boolean verified
) {
}
