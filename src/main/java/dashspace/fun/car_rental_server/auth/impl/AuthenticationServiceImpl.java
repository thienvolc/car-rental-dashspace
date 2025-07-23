package dashspace.fun.car_rental_server.auth.impl;

import dashspace.fun.car_rental_server.auth.AuthenticationService;
import dashspace.fun.car_rental_server.auth.factory.UserFactory;
import dashspace.fun.car_rental_server.auth.request.AuthenticationRequest;
import dashspace.fun.car_rental_server.auth.request.ClientDeviceInfomation;
import dashspace.fun.car_rental_server.auth.request.RefreshRequest;
import dashspace.fun.car_rental_server.auth.request.RenterRegistrationRequest;
import dashspace.fun.car_rental_server.auth.response.AuthenticationResponse;
import dashspace.fun.car_rental_server.auth.util.TokenIssuer;
import dashspace.fun.car_rental_server.common.util.message.contact.MessageContact;
import dashspace.fun.car_rental_server.exception.BusinessException;
import dashspace.fun.car_rental_server.session.SessionService;
import dashspace.fun.car_rental_server.session.request.SessionCommand;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.user.UserRepository;
import dashspace.fun.car_rental_server.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dashspace.fun.car_rental_server.exception.ErrorCode.EMAIL_ALREADY_EXISTS;
import static dashspace.fun.car_rental_server.exception.ErrorCode.EMAIL_NOT_VERIFIED;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final SessionService sessionService;
    private final VerificationService verificationService;

    private final TokenIssuer tokenIssuer;
    private final UserFactory userFactory;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registerRenter(RenterRegistrationRequest request) {
        ensureEmailIsAvailable(request.email());
        ensureEmailVerifiedByCode(request.email(), request.otpCode());
        User user = this.userFactory.createUserWithRoleRenter(request);
        this.userRepository.save(user);
    }

    private void ensureEmailIsAvailable(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }

    private void ensureEmailVerifiedByCode(String email, String otpCode) {
        MessageContact contact = MessageContact.fromEmail(email);
        if (!this.verificationService.isRegistrationOtpValid(contact, otpCode)) {
            throw new BusinessException(EMAIL_NOT_VERIFIED);
        }
    }

    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request,
                                        ClientDeviceInfomation deviceInfo) {

        User authenticated = authenticate(request);
        AuthenticationResponse response = this.tokenIssuer.issueTo(authenticated);
        SessionCommand sessionCommand = SessionCommand.builder()
                .userId(authenticated.getId())
                .deviceInfo(deviceInfo)
                .refreshToken(response.refreshToken())
                .build();
        this.sessionService.persistSession(sessionCommand);
        return response;
    }

    private User authenticate(AuthenticationRequest request) {
        Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),
                        request.password()));

        return (User) auth.getPrincipal();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        return this.tokenIssuer.refreshToken(request);
    }

    @Override
    public void logoutAll(Integer userId) {
        this.sessionService.invalidateAllSessions(userId);
    }
}
