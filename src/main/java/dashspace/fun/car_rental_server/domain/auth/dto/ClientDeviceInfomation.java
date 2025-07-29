package dashspace.fun.car_rental_server.domain.auth.dto;

import lombok.Builder;

@Builder
public record ClientDeviceInfomation(
        String deviceInfo,
        String ipAddress,
        String userAgent
) {
}
