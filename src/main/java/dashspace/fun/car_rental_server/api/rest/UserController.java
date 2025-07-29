package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import dashspace.fun.car_rental_server.app.service.ResponseFactory;
import dashspace.fun.car_rental_server.domain.auth.dto.request.VerificationRequest;
import dashspace.fun.car_rental_server.domain.user.dto.request.ForgotPasswordResetRequest;
import dashspace.fun.car_rental_server.domain.user.service.PasswordVerificationService;
import dashspace.fun.car_rental_server.domain.user.service.UserService;
import dashspace.fun.car_rental_server.domain.verification.dto.request.VerifyOtpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Management")
public class UserController {

    private final UserService service;
    private final PasswordVerificationService verificationService;

    private final ResponseFactory responseFactory;

    @Operation(summary = "Send OTP for forgot password")
    @PostMapping("/forgot-password/verifications")
    public ResponseDto sendForgotPasswordOtp(@Valid @RequestBody VerificationRequest request) {
        var response = verificationService.sendForgotPasswordOtp(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Verify OTP for forgot password")
    @PatchMapping("/forgot-password/verifications")
    public ResponseDto verifyForgotPasswordOtp(@Valid @RequestBody VerifyOtpRequest request) {
        var response = verificationService.verifyForgotPasswordOtp(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Reset forgot password. Required valid password forgot opt code")
    @PutMapping("/me/forgot-password/reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseDto resetForgotPassword(@Valid @RequestBody ForgotPasswordResetRequest request) {
        service.resetForgotPassword(request);
        return responseFactory.success(ResponseCode.FORGOT_PASSWORD_RESET);
    }
}
