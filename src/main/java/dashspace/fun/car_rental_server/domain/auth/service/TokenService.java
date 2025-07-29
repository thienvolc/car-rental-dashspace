package dashspace.fun.car_rental_server.domain.auth.service;

import dashspace.fun.car_rental_server.domain.auth.dto.request.RefreshRequest;
import dashspace.fun.car_rental_server.domain.auth.dto.response.AuthenticationResponse;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.infrastructure.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;

    public AuthenticationResponse issueToken(User user) {
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        return AuthenticationResponse.of(accessToken, refreshToken);
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        String accessToken = jwtService.refreshAccessToken(request.refreshToken());
        return AuthenticationResponse.of(accessToken, request.refreshToken());
    }
}
