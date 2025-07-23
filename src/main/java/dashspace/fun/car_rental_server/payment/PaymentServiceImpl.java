package dashspace.fun.car_rental_server.payment;

import dashspace.fun.car_rental_server.payment.adapter.VNPayAdapter;
import dashspace.fun.car_rental_server.payment.dto.PaymentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final VNPayAdapter adapter;

    public String createPaymentUrl(PaymentCommand command, String clientIpAddress) {
        return this.adapter.generatePaymentUrl(command, clientIpAddress);
    }
}
