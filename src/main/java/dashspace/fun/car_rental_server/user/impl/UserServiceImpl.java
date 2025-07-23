package dashspace.fun.car_rental_server.user.impl;

import dashspace.fun.car_rental_server.common.util.ImageStorageService;
import dashspace.fun.car_rental_server.common.util.message.contact.Channel;
import dashspace.fun.car_rental_server.common.util.message.contact.MessageContact;
import dashspace.fun.car_rental_server.exception.BusinessException;
import dashspace.fun.car_rental_server.user.*;
import dashspace.fun.car_rental_server.user.request.CreateDrivingLicenseRequest;
import dashspace.fun.car_rental_server.user.request.ResetForgotPasswordRequest;
import dashspace.fun.car_rental_server.user.response.DrivingLicenseDto;
import dashspace.fun.car_rental_server.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static dashspace.fun.car_rental_server.exception.ErrorCode.OTP_NOT_VERIFIED;
import static dashspace.fun.car_rental_server.exception.ErrorCode.PASSWORD_SAME_AS_OLD;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // 2 years in seconds
    private static final long DRIVING_LICENSE_EXPIRATION = 2 * 365 * 24 * 60 * 60;

    private final UserRepository userRepository;
    private final DrivingLicenseRepository drivingLicenseRepository;

    private final VerificationService verificationService;
    private final ImageStorageService imageStorageService;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userEmail)
            throws UsernameNotFoundException {

        return tryFindUserByEmail(userEmail);
    }

    User tryFindUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void resetForgotPassword(ResetForgotPasswordRequest request) {
        ensureForgotPasswordOtpIsValid(request);
        ensurePasswordNotSameAsOld(request);
        updatePassword(request);
    }

    private void ensurePasswordNotSameAsOld(ResetForgotPasswordRequest request) {
        User user = tryFindUserByEmail(request.email());
        if (user.getPasswordHash().equals(
                passwordEncoder.encode(request.newPassword()))) {
            throw new BusinessException(PASSWORD_SAME_AS_OLD);
        }
    }

    private void ensureForgotPasswordOtpIsValid(
            ResetForgotPasswordRequest request) {

        MessageContact messageContact = new MessageContact(request.email(), Channel.EMAIL);
        if (!this.verificationService.isForgotPasswordOtpValid(messageContact,
                request.otpCode())) {
            throw new BusinessException(OTP_NOT_VERIFIED);
        }
    }

    private void updatePassword(ResetForgotPasswordRequest request) {
        User user = tryFindUserByEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        user.setPasswordChangedAt(Instant.now());
        this.userRepository.save(user);
    }

    @Override
    public DrivingLicenseDto createDrivingLicense(Integer userId,
                                                  CreateDrivingLicenseRequest request) {

        String frontImageUrl = this.imageStorageService.upload(request.frontImage());
        String backImageUrl = this.imageStorageService.upload(request.backImage());
        DrivingLicense dl = this.userMapper.toDrivingLicense(request.credentials(),
                frontImageUrl, backImageUrl);
        User userRef = this.userRepository.getReferenceById(userId);
        dl.setOwner(userRef);
        dl.setExpiryDate(Instant.now().plusSeconds(DRIVING_LICENSE_EXPIRATION));

        this.drivingLicenseRepository.save(dl);

        return this.userMapper.toDrivingLicenseDto(dl);
    }
}
