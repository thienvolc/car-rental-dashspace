package dashspace.fun.car_rental_server.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "validation.authentication.email.blank")
        @Email(message = "validation.authentication.email.format")
        @Schema(example = "thienvo@gmail.com")
        String email,

        @NotBlank(message = "validation.authentication.password.blank")
        @Schema(example = "12345678")
        String password,

        String deviceInfo
) {
}
