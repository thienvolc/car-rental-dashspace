package dashspace.fun.car_rental_server.verification;

import dashspace.fun.car_rental_server.verification.enums.OtpVerificationPurpose;
import dashspace.fun.car_rental_server.verification.enums.OtpVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Integer> {
    Optional<OtpVerification> findByEmailAndCodeAndPurposeAndStatus(
            String email, String code, OtpVerificationPurpose purpose,
            OtpVerificationStatus status);

    boolean existsByEmailAndCodeAndStatusAndPurposeAndExpiresAtAfter(
            String destination, String code, OtpVerificationStatus status,
            OtpVerificationPurpose purpose, Instant now);
    boolean existsByEmailAndStatusAndPurposeAndExpiresAtAfter(String destination, OtpVerificationStatus otpVerificationStatus, OtpVerificationPurpose otpVerificationPurpose, Instant now);
}
