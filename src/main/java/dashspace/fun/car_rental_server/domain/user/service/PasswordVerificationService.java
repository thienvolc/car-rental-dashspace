package dashspace.fun.car_rental_server.domain.user.service;

import dashspace.fun.car_rental_server.app.aop.BusinessException;
import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.domain.auth.dto.request.VerificationRequest;
import dashspace.fun.car_rental_server.domain.verification.service.EmailSenderService;
import dashspace.fun.car_rental_server.domain.verification.util.OtpGenerator;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import dashspace.fun.car_rental_server.domain.verification.constant.VerificationPurpose;
import dashspace.fun.car_rental_server.domain.verification.constant.VerificationStatus;
import dashspace.fun.car_rental_server.domain.verification.dto.request.VerifyOtpRequest;
import dashspace.fun.car_rental_server.domain.verification.dto.response.VerificationResponse;
import dashspace.fun.car_rental_server.domain.verification.entity.OtpVerification;
import dashspace.fun.car_rental_server.domain.verification.mapper.OtpVerificationMapper;
import dashspace.fun.car_rental_server.domain.verification.repository.OtpVerificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static dashspace.fun.car_rental_server.domain.common.constant.ResponseCode.EMAIL_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class PasswordVerificationService {

    private static final int OTP_EXPIRATION = 300; // 5 minutes

    private final EmailSenderService emailSenderService;
    private final OtpVerificationMapper mapper;

    private final UserRepository userRepository;
    private final OtpVerificationRepository repository;

    public VerificationResponse sendForgotPasswordOtp(VerificationRequest request) {
        validateEmailIsAvailable(request.recepient());

        var verification = createOtpVerification(request.recepient());
        emailSenderService.sendForgotPasswordOtp(verification.getEmail(), verification.getCode());
        return mapper.toResponse(verification);
    }

    private void validateEmailIsAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }

    private OtpVerification createOtpVerification(String email) {
        var verification = OtpVerification.builder()
                .email(email)
                .code(OtpGenerator.generate6Digit())
                .purpose(VerificationPurpose.FORGOT_PASSWORD)
                .expiresAt(Instant.now().plusSeconds(OTP_EXPIRATION))
                .build();

        return repository.save(verification);
    }

    public VerificationResponse verifyForgotPasswordOtp(VerifyOtpRequest request) {
        var verification = tryGetForgotPasswordOtp(request.recepient(), request.code());

        if (!verification.getCode().equals(request.code())) {
            throw new BusinessException(ResponseCode.OTP_INVALID);
        }

        if (verification.getExpiresAt().isBefore(Instant.now())) {
            markExpired(verification);
            throw new BusinessException(ResponseCode.OTP_EXPIRED);
        }

        markVerified(verification);
        return mapper.toResponse(verification);
    }

    private void markExpired(OtpVerification verification) {
        verification.setStatus(VerificationStatus.EXPIRED);
        repository.save(verification);
    }

    private void markVerified(OtpVerification verification) {
        verification.setStatus(VerificationStatus.VERIFIED);
        repository.save(verification);
    }

    public void validateForgotPasswordOtp(String email, String code) {
        var verification = tryGetForgotPasswordOtp(email, code);

        if (!verification.isVerified()) {
            throw new BusinessException(ResponseCode.OTP_NOT_VERIFIED);
        }

        if (verification.getExpiresAt().isBefore(Instant.now())) {
            throw new BusinessException(ResponseCode.OTP_EXPIRED);
        }
    }

    private OtpVerification tryGetForgotPasswordOtp(String email, String code) {
        return repository.findForgotPasswordOtp(email, code)
                .orElseThrow(() -> new EntityNotFoundException("otp_verification.not_found"));
    }
}
