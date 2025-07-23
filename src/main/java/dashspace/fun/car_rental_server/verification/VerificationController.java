package dashspace.fun.car_rental_server.verification;

import dashspace.fun.car_rental_server.common.ApplicationResponse;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.verification.request.ForgotPasswordRequest;
import dashspace.fun.car_rental_server.verification.request.HostRegistrationVerificationRequest;
import dashspace.fun.car_rental_server.verification.request.VerificationRequest;
import dashspace.fun.car_rental_server.verification.request.VerifyOtpRequest;
import dashspace.fun.car_rental_server.verification.response.VerificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verification")
@Tag(name = "OTP Verification")
public class VerificationController {
    private final VerificationService verificationService;

    @Operation(summary = "Send OTP for registration")
    @PostMapping("/registration")
    public ResponseEntity<ApplicationResponse> sendRegistrationOtp(
            @Valid @RequestBody VerificationRequest request) {

        this.verificationService.sendRegistrationOtp(request);
        return ResponseEntity.ok(ApplicationResponse.success("otp.sent.success"));
    }

    @Operation(summary = "Verify OTP for registration")
    @PostMapping("/registration/verify")
    public ResponseEntity<ApplicationResponse> verifyRegistrationOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        VerificationResponse response = this.verificationService.verifyRegistrationOtp(
                request);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "otp.verify.success"));
    }

    @Operation(summary = "Send OTP for forgot password")
    @PostMapping("/password/forgot")
    public ResponseEntity<ApplicationResponse> sendForgotPasswordOtp(
            @Valid @RequestBody ForgotPasswordRequest request) {

        this.verificationService.sendForgotPasswordOtp(request);
        return ResponseEntity.ok(ApplicationResponse.success("otp.sent.success"));
    }

    @Operation(summary = "Verify OTP for forgot password")
    @PostMapping("/password/forgot/verify")
    public ResponseEntity<ApplicationResponse> verifyForgotPasswordOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        VerificationResponse response = this.verificationService.verifyForgotPasswordOtp(
                request);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "otp.verify.success"));
    }

    @Operation(summary = "Send OTP for host registration")
    @PostMapping("/registraion/host")
    public ResponseEntity<ApplicationResponse> sendHostRegistrationOtp(
            @Valid @RequestBody HostRegistrationVerificationRequest request,
            @AuthenticationPrincipal User currentUser) {

        this.verificationService.sendHostRegistrationOtp(currentUser.getId(), request);
        return ResponseEntity.ok(ApplicationResponse.success("otp.sent.success"));
    }

    @Operation(summary = "Verify OTP for host registration")
    @PostMapping("/registration/host/verify")
    public ResponseEntity<ApplicationResponse> verifyHostRegistrationOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        VerificationResponse response =
                this.verificationService.verifyHostRegistrationOtp(request);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "otp.verify.success"));
    }
}
