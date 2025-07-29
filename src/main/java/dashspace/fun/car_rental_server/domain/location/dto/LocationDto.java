package dashspace.fun.car_rental_server.domain.location.dto;

import lombok.Builder;

@Builder
public record LocationDto(
        String province,
        String district,
        String ward,
        String addressDetail,
        double latitude,
        double longitude
) {
}
