package dashspace.fun.car_rental_server.domain.auth.dto.request;

import dashspace.fun.car_rental_server.domain.verification.constant.Channel;

public record VerificationRequest(
        String recepient,
        Channel channel
) {
}
