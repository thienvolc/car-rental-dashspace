package dashspace.fun.car_rental_server.domain.auth.mapper;

import dashspace.fun.car_rental_server.domain.auth.dto.SessionDto;
import dashspace.fun.car_rental_server.domain.auth.entity.Session;
import org.springframework.stereotype.Service;

@Service
public class SessionMapper {

    public Session toSession(SessionDto sessionDto) {
        return Session.builder()
                .refreshToken(sessionDto.refreshToken())
                .ipAddress(sessionDto.ipAddress())
                .userAgent(sessionDto.userAgent())
                .deviceInfo(sessionDto.deviceInfo())
                .refreshTokenExpires(sessionDto.expiresAt())
                .build();
    }
}
