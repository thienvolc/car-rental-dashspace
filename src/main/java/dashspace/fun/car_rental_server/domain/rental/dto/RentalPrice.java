package dashspace.fun.car_rental_server.domain.rental.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RentalPrice(
        BigDecimal totalAmount,
        BigDecimal depositAmount,
        BigDecimal subtotal,
        String currency
) {
}
