package dashspace.fun.car_rental_server.session;

import dashspace.fun.car_rental_server.security.JwtHandler;
import dashspace.fun.car_rental_server.session.request.SessionCommand;
import dashspace.fun.car_rental_server.session.response.SessionDto;
import dashspace.fun.car_rental_server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionMapper {

    private final JwtHandler jwtHandler;

    public SessionDto toSessionDto(SessionCommand command) {
        return SessionDto.builder()
                .userId(command.getUserId())
                .refreshToken(command.getRefreshToken())
                .deviceInfo(command.getDeviceInfo().deviceInfo())
                .userAgent(command.getDeviceInfo().userAgent())
                .ipAddress(command.getDeviceInfo().ipAddress())
                .expiresAt(this.jwtHandler.extractExpiration(command.getRefreshToken()))
                .build();
    }

    public Session toSession(SessionDto sessionDto, User userRef) {
        return Session.builder()
                .user(userRef)
                .refreshToken(sessionDto.refreshToken())
                .ipAddress(sessionDto.ipAddress())
                .userAgent(sessionDto.userAgent())
                .deviceInfo(sessionDto.deviceInfo())
                .refreshTokenExpires(sessionDto.expiresAt())
                .build();
    }
}
