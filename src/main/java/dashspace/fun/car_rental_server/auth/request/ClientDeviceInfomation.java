package dashspace.fun.car_rental_server.auth.request;

import lombok.Builder;

@Builder
public record ClientDeviceInfomation(
        String deviceInfo,
        String ipAddress,
        String userAgent
) {
}
