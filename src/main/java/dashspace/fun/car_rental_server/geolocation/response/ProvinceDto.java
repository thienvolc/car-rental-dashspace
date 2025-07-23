package dashspace.fun.car_rental_server.geolocation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProvinceDto(
        String name,
        Integer code,

        @JsonProperty("division_type")
        String divisionType
) {
}
