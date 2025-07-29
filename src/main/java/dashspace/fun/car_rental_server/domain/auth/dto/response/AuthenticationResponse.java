package dashspace.fun.car_rental_server.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    public static AuthenticationResponse of(String accessToken, String refreshToken) {
        return new AuthenticationResponse(accessToken, refreshToken, "Bearer");
    }
}
