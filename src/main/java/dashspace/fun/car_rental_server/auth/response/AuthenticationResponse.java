package dashspace.fun.car_rental_server.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType
) {

    public static AuthenticationResponse of(String accessToken, String refreshToken) {
        return new AuthenticationResponse(accessToken, refreshToken, "Bearer");
    }
}
