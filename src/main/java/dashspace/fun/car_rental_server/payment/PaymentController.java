package dashspace.fun.car_rental_server.payment;

import dashspace.fun.car_rental_server.common.ApplicationResponse;
import dashspace.fun.car_rental_server.payment.dto.PaymentCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("payments")
@Tag(name = "Payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Create a new payment")
    @PostMapping
    public ResponseEntity<ApplicationResponse> createPayment(
            @Valid @RequestBody PaymentCommand command,
            HttpServletRequest servletRequest) {

        String paymentUrl = this.paymentService.createPaymentUrl(command,
                servletRequest.getRemoteAddr());
        return ResponseEntity.ok(ApplicationResponse.success(paymentUrl,
                "payment.create.success"));
    }
}
