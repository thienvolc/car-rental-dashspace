package dashspace.fun.car_rental_server.session.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record SessionDto(
        Integer userId,
        String ipAddress,
        String refreshToken,
        String userAgent,
        String deviceInfo,
        Instant expiresAt
) {
}
