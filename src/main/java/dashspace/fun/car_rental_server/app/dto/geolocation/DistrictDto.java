package dashspace.fun.car_rental_server.app.dto.geolocation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DistrictDto(
        String name,
        Integer code,

        @JsonProperty("division_type")
        String divisionType
) {
}
