package dashspace.fun.car_rental_server.app.dto.geolocation;

import lombok.Builder;

@Builder
public record WardDto(
        String name,
        Integer code
) {
}
