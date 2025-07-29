package dashspace.fun.car_rental_server.domain.auth.service;

import dashspace.fun.car_rental_server.app.aop.BusinessException;
import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.domain.auth.dto.ClientDeviceInfomation;
import dashspace.fun.car_rental_server.domain.auth.dto.SessionDto;
import dashspace.fun.car_rental_server.domain.auth.dto.UserDto;
import dashspace.fun.car_rental_server.domain.auth.dto.request.AuthenticationRequest;
import dashspace.fun.car_rental_server.domain.auth.dto.request.RefreshRequest;
import dashspace.fun.car_rental_server.domain.auth.dto.request.UserRegistrationRequest;
import dashspace.fun.car_rental_server.domain.auth.dto.response.AuthenticationResponse;
import dashspace.fun.car_rental_server.domain.role.constant.RoleNames;
import dashspace.fun.car_rental_server.domain.role.repository.RoleRepository;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.user.mapper.UserMapper;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import dashspace.fun.car_rental_server.infrastructure.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RegistrationVerificationService verificationService;
    private final TokenService tokenService;
    private final SessionService sessionService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserDto register(UserRegistrationRequest request) {
        validateRequest(request);
        var user = userFactory.createUserWithRenterRole(request);
        return userMapper.toResponse(user);
    }

    private void validateRequest(UserRegistrationRequest request) {
        validateEmailIsAvailable(request.email());
        verificationService.validateRegistrationOtp(request.email(), request.otpCode());
    }

    private void validateEmailIsAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ResponseCode.EMAIL_ALREADY_EXISTS);
        }
    }

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request, ClientDeviceInfomation deviceInfo) {
        User authenticated = authenticate(request);
        AuthenticationResponse response = tokenService.issueToken(authenticated);
        var expiresAt = jwtService.extractExpiration(response.getRefreshToken());

        var sessionDto = SessionDto.builder()
                .userId(authenticated.getId())
                .refreshToken(response.getRefreshToken())
                .expiresAt(expiresAt)
                .deviceInfo(deviceInfo.deviceInfo())
                .userAgent(deviceInfo.userAgent())
                .ipAddress(deviceInfo.ipAddress())
                .build();

        sessionService.persistSession(sessionDto);

        return response;
    }

    private User authenticate(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        return (User) auth.getPrincipal();
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        return tokenService.refreshToken(request);
    }

    public void logoutAllByUserId(Integer userId) {
        sessionService.deleteSessionsByUserId(userId);
    }

    public void assignHostRoleToUser(Integer userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND));
        var role = roleRepository.findByName(RoleNames.HOST.getValue())
                .orElseThrow(() -> new EntityNotFoundException("role.host.not_found"));

        user.addRole(role);
        userRepository.save(user);
    }
}
