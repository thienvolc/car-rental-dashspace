package dashspace.fun.car_rental_server.domain.identity_document.service;

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

@Service
@RequiredArgsConstructor
public class HostRegistrationVerificationService {

    private static final int HOST_REGISTRATION_EXPIRATION = 900; // for 15 minutes

    private final EmailSenderService emailSenderService;
    private final OtpVerificationMapper mapper;

    private final OtpVerificationRepository repository;
    private final UserRepository userRepository;

    public VerificationResponse sendHostRegistrationOtp(VerificationRequest request, Integer userId) {
        validateEmailIsAvailable(request.recepient(), userId);

        var verification = createOtpVerification(request.recepient());
        emailSenderService.sendHostRegistrationOtp(verification.getEmail(), verification.getCode());
        return mapper.toResponse(verification);
    }

    private void validateEmailIsAvailable(String email, Integer userId) {
        if (isEmailOwnedByUser(email, userId)) {
            return;
        }
        validateEmailNotInUse(email);
    }

    private boolean isEmailOwnedByUser(String email, Integer userId) {
        return userRepository.existsByEmailAndId(email, userId);
    }

    private void validateEmailNotInUse(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ResponseCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private OtpVerification createOtpVerification(String email) {
        var verification = OtpVerification.builder()
                .email(email)
                .code(OtpGenerator.generate6Digit())
                .purpose(VerificationPurpose.HOST_REGISTRATION)
                .expiresAt(Instant.now().plusSeconds(HOST_REGISTRATION_EXPIRATION))
                .build();

        return repository.save(verification);
    }

    public VerificationResponse verifyHostRegistrationOtp(VerifyOtpRequest request) {
        var verification = tryGetHostRegistrationOtp(request.recepient(), request.code());

        if (!verification.getCode().equals(request.code())) {
            throw new BusinessException(ResponseCode.OTP_INVALID);
        }

        if (verification.getExpiresAt().isBefore(Instant.now())) {
            markAsExpired(verification);
            throw new BusinessException(ResponseCode.OTP_EXPIRED);
        }

        markAsVerified(verification);
        return mapper.toResponse(verification);
    }

    private void markAsExpired(OtpVerification verification) {
        verification.setStatus(VerificationStatus.EXPIRED);
        repository.save(verification);
    }

    private void markAsVerified(OtpVerification verification) {
        verification.setStatus(VerificationStatus.VERIFIED);
        repository.save(verification);
    }

    public void validateHostRegistrationOtp(String email, String code) {
        var verification = tryGetHostRegistrationOtp(email, code);

        if (!verification.isVerified()) {
            throw new BusinessException(ResponseCode.OTP_NOT_VERIFIED);
        }

        if (verification.getExpiresAt().isBefore(Instant.now())) {
            throw new BusinessException(ResponseCode.OTP_EXPIRED);
        }
    }

    private OtpVerification tryGetHostRegistrationOtp(String email, String code) {
        return repository.findHostRegistrationOtp(email, code)
                .orElseThrow(() -> new EntityNotFoundException("otp_verification.not_found"));
    }
}
