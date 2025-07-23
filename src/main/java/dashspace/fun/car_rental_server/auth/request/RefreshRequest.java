package dashspace.fun.car_rental_server.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank(message = "validation.authentication.refresh_token.blank")
        String refreshToken
) {
}
