package dashspace.fun.car_rental_server.domain.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VehicleImageDto(

        @JsonProperty("image_url")
        String imageUrl,

        @JsonProperty("sort_order")
        int sortOrder
) {
}
