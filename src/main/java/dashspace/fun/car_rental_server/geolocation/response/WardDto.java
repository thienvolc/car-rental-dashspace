package dashspace.fun.car_rental_server.geolocation.response;

import lombok.Builder;

@Builder
public record WardDto(
        String name,
        Integer code
) {
}
