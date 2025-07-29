package dashspace.fun.car_rental_server.domain.verification.mapper;

import dashspace.fun.car_rental_server.domain.verification.dto.response.VerificationResponse;
import dashspace.fun.car_rental_server.domain.verification.entity.OtpVerification;
import dashspace.fun.car_rental_server.domain.verification.service.VerificationMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpVerificationMapper {

    private final VerificationMessageFactory messageFactory;

    public VerificationResponse toResponse(OtpVerification verification) {
        String message = messageFactory.fromStatus(verification.getStatus());
        return VerificationResponse.builder()
                .id(verification.getId())
                .status(verification.getStatus())
                .purpose(verification.getPurpose())
                .email(verification.getEmail())
                .message(message)
                .verified(verification.isVerified())
                .build();
    }
}
