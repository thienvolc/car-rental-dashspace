package dashspace.fun.car_rental_server.domain.rental.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dashspace.fun.car_rental_server.domain.rental.constant.RentalStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RentalDto(
        Integer id,
        String requestId,
        Integer vehicleId,
        Integer userId,
        LocalDate pickupDate,
        LocalDate returnDate,
        RentalStatus status,
        String pickupLocation,
        String returnLocation,
        BigDecimal totalAmount,
        BigDecimal depositAmount,
        String note
) {
}
