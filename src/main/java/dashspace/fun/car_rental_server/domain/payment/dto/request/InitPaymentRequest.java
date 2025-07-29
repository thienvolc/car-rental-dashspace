package dashspace.fun.car_rental_server.domain.payment.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InitPaymentRequest(
        Integer userId,
        BigDecimal amount,
        String txnRef,
        String requestId,
        String ipAddress
) {
}
