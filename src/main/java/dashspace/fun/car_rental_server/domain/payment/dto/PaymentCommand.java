package dashspace.fun.car_rental_server.domain.payment.dto;

public record PaymentCommand(
        String language,
        String bankCode,
        Double amount
) {
}
