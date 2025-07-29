package dashspace.fun.car_rental_server.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ForgotPasswordResetRequest(

        String email,

        @JsonProperty("new_password")
        String newPassword,

        @JsonProperty("otp_code")
        String otpCode
) {
}
