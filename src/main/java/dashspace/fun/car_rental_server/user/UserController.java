package dashspace.fun.car_rental_server.user;

import dashspace.fun.car_rental_server.common.ApplicationResponse;
import dashspace.fun.car_rental_server.user.request.CreateDrivingLicenseRequest;
import dashspace.fun.car_rental_server.user.request.DrivingLicenseCredentials;
import dashspace.fun.car_rental_server.user.request.ResetForgotPasswordRequest;
import dashspace.fun.car_rental_server.user.response.DrivingLicenseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Management")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Reset forgot password",
            description =
                    "Required valid password forgot opt code from verification service")
    @PutMapping("/me/password/forgot/reset")
    public ResponseEntity<ApplicationResponse> resetForgotPassword(
            @Valid @RequestBody ResetForgotPasswordRequest request) {

        this.userService.resetForgotPassword(request);
        ApplicationResponse response = ApplicationResponse.success(
                "password.reset.success");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a driving license")
    @PutMapping("/me/driving-license")
    public ResponseEntity<ApplicationResponse> createDrivingLicense(
            @NotNull @RequestPart("front_image") MultipartFile frontImage,
            @NotNull @RequestPart("back_image") MultipartFile backImage,
            @Valid @RequestBody DrivingLicenseCredentials credentials,
            @AuthenticationPrincipal User user) {

        CreateDrivingLicenseRequest request =
                this.userMapper.toCreateDrivingLicenseRequest(credentials, frontImage,
                        backImage);
        DrivingLicenseDto drivingLicense = this.userService.createDrivingLicense(
                user.getId(), request);
        return ResponseEntity.ok(ApplicationResponse.success(drivingLicense,
                "driving_license.create.success"));
    }
}
