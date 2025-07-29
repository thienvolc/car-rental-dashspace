package dashspace.fun.car_rental_server.domain.rental.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RentalRequest(

        @NotBlank(message = "validation.rental.vehicle_id.blank")
        Integer vehicleId,

        @NotBlank(message = "validation.rental.request_id.blank")
        String requestId,

        @NotNull(message = "validation.rental.pickup_date.blank")
        LocalDate pickupDate,

        @NotNull(message = "validation.rental.return_date.blank")
        LocalDate returnDate,

        @Length(max = 500, message = "validation.rental.note.length.out_of_range")
        String note
) {
}
