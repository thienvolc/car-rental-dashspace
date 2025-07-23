package dashspace.fun.car_rental_server.vehicle.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VehicleImageDto(
        @JsonProperty("image_url")
        String imageUrl,

        @JsonProperty("sort_order")
        int sortOrder
) {}
