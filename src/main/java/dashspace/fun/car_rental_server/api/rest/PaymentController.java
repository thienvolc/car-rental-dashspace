package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.domain.payment.dto.response.IpnResponse;
import dashspace.fun.car_rental_server.domain.payment.service.IpnHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("payments")
@Tag(name = "Payment")
public class PaymentController {

    private final IpnHandler ipnHandler;

    @GetMapping("/vnpay_ipn")
    IpnResponse processIpn(@RequestParam Map<String, String> params) {
        return ipnHandler.process(params);
    }
}
