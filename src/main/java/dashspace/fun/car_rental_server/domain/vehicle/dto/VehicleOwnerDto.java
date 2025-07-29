package dashspace.fun.car_rental_server.domain.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record VehicleOwnerDto(

        Integer id,

        String name,

        @JsonProperty("avatar_url")
        String avatarUrl
) {
}
