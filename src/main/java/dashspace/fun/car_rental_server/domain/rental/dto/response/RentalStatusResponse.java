package dashspace.fun.car_rental_server.domain.rental.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dashspace.fun.car_rental_server.domain.rental.constant.RentalStatus;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RentalStatusResponse(
        Integer rentalId,
        Integer userId,
        Integer vehicleId,
        RentalStatus status
) {
}
