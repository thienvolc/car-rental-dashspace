package dashspace.fun.car_rental_server.domain.user.service;

import dashspace.fun.car_rental_server.app.aop.BusinessException;
import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.domain.user.dto.request.ForgotPasswordResetRequest;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordVerificationService verificationService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return tryFindUserByEmail(email);
    }

    public void resetForgotPassword(ForgotPasswordResetRequest request) {
        validateRequest(request);
        updatePassword(request);
    }

    private void validateRequest(ForgotPasswordResetRequest request) {
        verificationService.validateForgotPasswordOtp(request.email(), request.otpCode());
        validatePasswordNotSameAsOld(request);
    }

    private void validatePasswordNotSameAsOld(ForgotPasswordResetRequest request) {
        var user = tryFindUserByEmail(request.email());
        boolean isSame = passwordEncoder.matches(request.newPassword(), user.getPassword());
        if (isSame) {
            throw new BusinessException(ResponseCode.PASSWORD_SAME_AS_OLD);
        }
    }

    private void updatePassword(ForgotPasswordResetRequest request) {
        var user = tryFindUserByEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        user.setPasswordChangedAt(Instant.now());
        repository.save(user);
    }

    private User tryFindUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND));
    }

    public User tryFindUserById(Integer userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND));
    }
}
