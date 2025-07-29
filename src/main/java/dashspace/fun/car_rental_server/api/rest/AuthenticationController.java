package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import dashspace.fun.car_rental_server.app.service.ResponseFactory;
import dashspace.fun.car_rental_server.app.util.RequestUtil;
import dashspace.fun.car_rental_server.domain.auth.dto.ClientDeviceInfomation;
import dashspace.fun.car_rental_server.domain.auth.dto.request.AuthenticationRequest;
import dashspace.fun.car_rental_server.domain.auth.dto.request.RefreshRequest;
import dashspace.fun.car_rental_server.domain.auth.dto.request.UserRegistrationRequest;
import dashspace.fun.car_rental_server.domain.auth.dto.request.VerificationRequest;
import dashspace.fun.car_rental_server.domain.auth.service.AuthenticationService;
import dashspace.fun.car_rental_server.domain.auth.service.RegistrationVerificationService;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.verification.dto.request.VerifyOtpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;
    private final RegistrationVerificationService verificationService;
    private final ResponseFactory responseFactory;

    @Operation(summary = "Send OTP for registration")
    @PostMapping("/register/verifications")
    public ResponseDto sendRegistrationOtp(@Valid @RequestBody VerificationRequest request) {
        var response = verificationService.sendRegistrationOtp(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Verify OTP for registration")
    @PatchMapping("/register/verifications")
    public ResponseDto verifyRegistrationOtp(@Valid @RequestBody VerifyOtpRequest request) {
        var response = verificationService.verifyRegistrationOtp(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Register a new user with default renter role. Required valid opt code")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto register(@Valid @RequestBody UserRegistrationRequest request) {
        var response = service.register(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseDto login(@Valid @RequestBody AuthenticationRequest request,
                             HttpServletRequest httpServletRequest) {

        var deviceInfo = ClientDeviceInfomation.builder()
                .deviceInfo(request.deviceInfo())
                .ipAddress(RequestUtil.getIpAddress(httpServletRequest))
                .userAgent(httpServletRequest.getHeader("User-Agent"))
                .build();

        var response = service.login(request, deviceInfo);
        return responseFactory.success(response);
    }

    @Operation(summary = "Refresh authentication access token")
    @PostMapping("/refresh")
    public ResponseDto refresh(@Valid @RequestBody RefreshRequest request) {
        var response = service.refreshToken(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Logout all sessions of current user")
    @PostMapping("/logout-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseDto logoutAll(@AuthenticationPrincipal User currentUser) {
        service.logoutAllByUserId(currentUser.getId());
        return responseFactory.success(ResponseCode.LOGOUT_ALL);
    }
}
