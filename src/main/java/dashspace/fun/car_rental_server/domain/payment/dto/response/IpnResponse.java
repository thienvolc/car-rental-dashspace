package dashspace.fun.car_rental_server.domain.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IpnResponse(

        @JsonProperty("RspCode")
        String responseCode,

        @JsonProperty("Message")
        String message
) {
}
