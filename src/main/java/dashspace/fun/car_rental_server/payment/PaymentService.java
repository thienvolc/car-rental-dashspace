package dashspace.fun.car_rental_server.payment;

import dashspace.fun.car_rental_server.payment.dto.PaymentCommand;

public interface PaymentService {
    String createPaymentUrl(PaymentCommand command, String clientIpAddress);
}
