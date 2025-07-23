package dashspace.fun.car_rental_server.auth.util;

import dashspace.fun.car_rental_server.auth.request.RefreshRequest;
import dashspace.fun.car_rental_server.auth.response.AuthenticationResponse;
import dashspace.fun.car_rental_server.security.JwtHandler;
import dashspace.fun.car_rental_server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenIssuer {

    private final JwtHandler jwtHandler;

    public AuthenticationResponse issueTo(User user) {
        String accessToken = this.jwtHandler.generateAccessToken(user.getUsername());
        String refreshToken = this.jwtHandler.generateRefreshToken(user.getUsername());
        return AuthenticationResponse.of(accessToken, refreshToken);
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        String accessToken = this.jwtHandler.refreshAccessToken(request.refreshToken());
        return AuthenticationResponse.of(accessToken, request.refreshToken());
    }
}
