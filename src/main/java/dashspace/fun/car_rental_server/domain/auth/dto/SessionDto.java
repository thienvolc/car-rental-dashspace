package dashspace.fun.car_rental_server.domain.auth.dto;

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
