package dashspace.fun.car_rental_server.location;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LocationDto(
        @NotNull String province,
        @NotNull String district,
        @NotNull String ward,
        @NotNull String addressDetail,
        @NotNull double latitude,
        @NotNull double longitude
) {}
