package dashspace.fun.car_rental_server.verification.impl;

import dashspace.fun.car_rental_server.common.util.message.contact.MessageContact;
import dashspace.fun.car_rental_server.common.util.message.factory.MessageSenderFactory;
import dashspace.fun.car_rental_server.common.util.message.sender.MessageSender;
import dashspace.fun.car_rental_server.exception.BusinessException;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.user.UserRepository;
import dashspace.fun.car_rental_server.verification.OtpVerification;
import dashspace.fun.car_rental_server.verification.OtpVerificationMapper;
import dashspace.fun.car_rental_server.verification.OtpVerificationRepository;
import dashspace.fun.car_rental_server.verification.VerificationService;
import dashspace.fun.car_rental_server.verification.enums.OtpVerificationPurpose;
import dashspace.fun.car_rental_server.verification.enums.OtpVerificationStatus;
import dashspace.fun.car_rental_server.verification.request.ForgotPasswordRequest;
import dashspace.fun.car_rental_server.verification.request.HostRegistrationVerificationRequest;
import dashspace.fun.car_rental_server.verification.request.VerificationRequest;
import dashspace.fun.car_rental_server.verification.request.VerifyOtpRequest;
import dashspace.fun.car_rental_server.verification.response.VerificationResponse;
import dashspace.fun.car_rental_server.verification.util.OtpGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static dashspace.fun.car_rental_server.exception.ErrorCode.*;
import static dashspace.fun.car_rental_server.verification.enums.OtpVerificationPurpose.*;
import static dashspace.fun.car_rental_server.verification.enums.OtpVerificationStatus.*;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private static final int OTP_EXPIRATION = 300; // 5 minutes
    private static final int HOST_REGISTRATION_EXPIRATION = 15 * 60; // 15p

    private final UserRepository userRepository;
    private final OtpVerificationRepository otpVerificationRepository;

    private final MessageSenderFactory senderFactory;
    private final OtpVerificationMapper otpVerificationMapper;

    @Override
    public void sendRegistrationOtp(VerificationRequest request) {
        MessageContact contact = new MessageContact(request.recepient(),
                request.channel());
        ensureContactIsAvailable(contact);

        OtpVerification otp = createOtpVerification(contact, REGISTRATION,
                OTP_EXPIRATION);
        this.senderFactory.getInstance(contact.getChannel())
                .sendRegistrationOtp(contact, otp.getCode());
    }

    private void ensureContactIsAvailable(MessageContact contact) {
        if (this.userRepository.existsByEmail(contact.getRecipient())) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }

    private OtpVerification createOtpVerification(MessageContact contact,
                                                  OtpVerificationPurpose purpose,
                                                  int expirationSec) {

        OtpVerification otp = OtpVerification.builder()
                .code(OtpGenerator.generate6Digit())
                .email(contact.getRecipient())
                .purpose(purpose)
                .expiresAt(Instant.now().plusSeconds(expirationSec))
                .build();

        return this.otpVerificationRepository.save(otp);
    }

    @Override
    public VerificationResponse verifyRegistrationOtp(VerifyOtpRequest request) {
        OtpVerification otp = tryFindPendingOtpVerification(request.recepient(),
                request.code(), REGISTRATION);
        verifyOtpExpiration(otp);

        markAsStatus(otp, VERIFIED);

        return this.otpVerificationMapper.mapToVerificationResponse(otp);
    }

    private OtpVerification tryFindPendingOtpVerification(
            String recepient, String code, OtpVerificationPurpose purpose) {

        return this.otpVerificationRepository
                .findByEmailAndCodeAndPurposeAndStatus(recepient, code, purpose, PENDING)
                .orElseThrow(() -> new EntityNotFoundException(
                        "otp_verification.not_found"));
    }

    private void verifyOtpExpiration(OtpVerification otp) throws BusinessException {
        if (otp.getExpiresAt().isBefore(Instant.now())) {
            markAsStatus(otp, EXPIRED);
            throw new BusinessException(OTP_EXPIRED);
        }
    }

    private void markAsStatus(OtpVerification otp, OtpVerificationStatus status) {
        otp.setStatus(status);
        this.otpVerificationRepository.save(otp);
    }

    @Override
    public boolean isRegistrationOtpValid(MessageContact contact, String code) {
        return this.otpVerificationRepository
                .existsByEmailAndCodeAndStatusAndPurposeAndExpiresAtAfter(
                        contact.getRecipient(), code, VERIFIED, REGISTRATION,
                        Instant.now());
    }

    @Override
    public boolean isForgotPasswordOtpValid(MessageContact contact, String code) {
        return this.otpVerificationRepository
                .existsByEmailAndCodeAndStatusAndPurposeAndExpiresAtAfter(
                        contact.getRecipient(), code, VERIFIED, FORGOT_PASSWORD,
                        Instant.now());
    }

    @Override
    public void sendForgotPasswordOtp(ForgotPasswordRequest request) {
        MessageContact contact = new MessageContact(request.recepient(),
                request.channel());
        ensureContactIsAvailable(contact);

        OtpVerification otp = createOtpVerification(contact, FORGOT_PASSWORD,
                OTP_EXPIRATION);
        this.senderFactory.getInstance(contact.getChannel())
                .sendForgotPasswordOtp(contact, otp.getCode());
    }

    @Override
    public VerificationResponse verifyForgotPasswordOtp(VerifyOtpRequest request) {
        OtpVerification otp = tryFindPendingOtpVerification(request.recepient(),
                request.code(), FORGOT_PASSWORD);
        verifyOtpExpiration(otp);

        markAsStatus(otp, VERIFIED);

        return this.otpVerificationMapper.mapToVerificationResponse(otp);
    }

    @Override
    public void sendHostRegistrationOtp(Integer userId,
                                        HostRegistrationVerificationRequest request) {

        MessageContact contact = new MessageContact(request.recepient(),
                request.channel());
        ensureContactIsAvailable(userId, contact.getRecipient());

        OtpVerification otp = createOtpVerification(contact, HOST_REGISTRATION,
                HOST_REGISTRATION_EXPIRATION);
        this.senderFactory.getInstance(contact.getChannel())
                .sendHostRegistrationVerificationOtp(contact, otp.getCode());
    }

    private void ensureContactIsAvailable(Integer userId, String email) {
        if (!verifyEmailOwnership(userId, email)) {
            ensureEmailNotInUse(email);
        }
    }

    private boolean verifyEmailOwnership(Integer userId, String email) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(EXISTING_USER_NOT_FOUND));

        return user.getEmail().equals(email);
    }

    private void ensureEmailNotInUse(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public VerificationResponse verifyHostRegistrationOtp(VerifyOtpRequest request) {
        OtpVerification otp = tryFindPendingOtpVerification(request.recepient(),
                request.code(), HOST_REGISTRATION);
        verifyOtpExpiration(otp);

        markAsStatus(otp, VERIFIED);

        return this.otpVerificationMapper.mapToVerificationResponse(otp);
    }

    @Override
    public boolean isHostRegistrationOtpValid(MessageContact target) {
        return this.otpVerificationRepository
                .existsByEmailAndStatusAndPurposeAndExpiresAtAfter(
                        target.getRecipient(), VERIFIED, HOST_REGISTRATION,
                        Instant.now());
    }
}
