package dashspace.fun.car_rental_server.payment.dto;

public record PaymentCommand(
        String language,
        String bankCode,
        double amount
) {
}
