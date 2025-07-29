package dashspace.fun.car_rental_server.domain.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record InitPaymentResponse(

        @JsonProperty("vnp_url")
        String vnpUrl
) {
}
