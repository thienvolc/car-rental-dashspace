package dashspace.fun.car_rental_server.domain.rental.dto.response;

import dashspace.fun.car_rental_server.domain.payment.dto.response.InitPaymentResponse;
import lombok.Builder;

@Builder
public record RentalResponse(
        RentalDto rental,
        InitPaymentResponse payment
) {
}
