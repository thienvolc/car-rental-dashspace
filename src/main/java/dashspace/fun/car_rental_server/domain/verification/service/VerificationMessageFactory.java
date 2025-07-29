package dashspace.fun.car_rental_server.domain.verification.service;

import dashspace.fun.car_rental_server.domain.verification.constant.VerificationMessage;
import dashspace.fun.car_rental_server.domain.verification.constant.VerificationStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VerificationMessageFactory {

    private final Map<VerificationStatus, String> messageMapper;

    VerificationMessageFactory() {
        this.messageMapper = Map.of(
                VerificationStatus.PENDING, VerificationMessage.PENDING.getMessage(),
                VerificationStatus.VERIFIED, VerificationMessage.VERIFIED.getMessage(),
                VerificationStatus.FAILED, VerificationMessage.FAILED.getMessage(),
                VerificationStatus.EXPIRED, VerificationMessage.EXPIRED.getMessage(),
                VerificationStatus.CANCELLED, VerificationMessage.CANCELLED.getMessage()
        );
    }

    public String fromStatus(VerificationStatus status) {
        return messageMapper.get(status);
    }
}
