package dashspace.fun.car_rental_server.app.dto.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Map;

@Builder
public record Meta(

        @JsonProperty("request_id")
        String requestId,

        String status,

        String message,

        @JsonProperty("service_id")
        String serviceId,

        @JsonProperty("extra_meta")
        Map<String, Object> extraMeta
) {
}
