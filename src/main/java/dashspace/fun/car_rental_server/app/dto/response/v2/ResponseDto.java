package dashspace.fun.car_rental_server.app.dto.response.v2;

import jakarta.annotation.Nullable;

public record ResponseDto(
        Meta meta,
        @Nullable Object data
) {
}
