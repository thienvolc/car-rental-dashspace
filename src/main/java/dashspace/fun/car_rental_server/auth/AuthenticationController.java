package dashspace.fun.car_rental_server.auth;

import dashspace.fun.car_rental_server.auth.request.AuthenticationRequest;
import dashspace.fun.car_rental_server.auth.request.RefreshRequest;
import dashspace.fun.car_rental_server.auth.request.RenterRegistrationRequest;
import dashspace.fun.car_rental_server.auth.response.AuthenticationResponse;
import dashspace.fun.car_rental_server.auth.request.ClientDeviceInfomation;
import dashspace.fun.car_rental_server.common.ApplicationResponse;
import dashspace.fun.car_rental_server.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final AuthenticationMapper authMapper;

    @Operation(
            summary = "Register a new user with role renter",
            description = "Required valid opt code from verification service"
    )
    @PostMapping("/register")
    public ResponseEntity<ApplicationResponse> registerRenter(
            @Valid @RequestBody RenterRegistrationRequest request) {

        this.authService.registerRenter(request);
        return ResponseEntity.status(CREATED).body(
                ApplicationResponse.success("user.register.success"));
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse> login(
            @Valid @RequestBody AuthenticationRequest request,
            HttpServletRequest servletRequest) {

        ClientDeviceInfomation deviceInfo = this.authMapper.mergeHostDeviceInfo(request,
                servletRequest);
        AuthenticationResponse response = this.authService.login(request, deviceInfo);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "user.login.success"));
    }

    @Operation(summary = "Refresh authentication access token")
    @PostMapping("/refresh")
    public ResponseEntity<ApplicationResponse> refresh(
            @Valid @RequestBody RefreshRequest request) {

        AuthenticationResponse response = this.authService.refreshToken(request);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "token.refresh.success"));
    }

    @Operation(summary = "Logout all sessions of current user")
    @PostMapping("/logout/all")
    public ResponseEntity<ApplicationResponse> logoutAll(
            @AuthenticationPrincipal User currentUser) {

        this.authService.logoutAll(currentUser.getId());
        return ResponseEntity.ok(ApplicationResponse.success("all.user.logout.success"));
    }
}
