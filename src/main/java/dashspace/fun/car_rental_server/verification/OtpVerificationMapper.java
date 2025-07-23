package dashspace.fun.car_rental_server.verification;

import dashspace.fun.car_rental_server.verification.response.VerificationResponse;
import dashspace.fun.car_rental_server.verification.util.VerificationMessage;
import org.springframework.stereotype.Service;

@Service
public class OtpVerificationMapper {

    public VerificationResponse mapToVerificationResponse(OtpVerification verification) {
        String message = VerificationMessage.fromStatus(verification.getStatus());

        return new VerificationResponse(verification.isVerified(), message);
    }
}
