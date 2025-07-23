package dashspace.fun.car_rental_server.verification.request;

import dashspace.fun.car_rental_server.common.util.message.contact.Channel;

public record VerificationRequest(
        String recepient,
        Channel channel
) {
}
