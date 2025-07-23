package dashspace.fun.car_rental_server.vehicle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VehicleOwnerDto(
        @NotNull Integer id,
        @NotNull String name,

        @JsonProperty("avatar_url")
        String avatarUrl
) {}
