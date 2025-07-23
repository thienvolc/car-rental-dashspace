package dashspace.fun.car_rental_server.auth.request;

import dashspace.fun.car_rental_server.user.validation.NonDisposableEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RenterRegistrationRequest(
        @NotBlank(message = "validation.authentication.email.blank")
        @Email(message = "validation.authentication.email.format")
        @NonDisposableEmail(message = "validation.authentication.email.disposable")
        @Schema(example = "thienvo@gmail.com")
        String email,

        @NotBlank(message = "validation.authentication.password.blank")
        @Size(min = 8, max = 72, message = "validation.authentication.password.weak")
        @Schema(example = "12345678")
        String password,

        @NotBlank(message = "validation.authentication.otp.blank")
        @Size(min = 6, max = 10, message = "validation.authentication.otp.length.out_of_range")
        @Schema(example = "123456")
        String otpCode
) {
}
