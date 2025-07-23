package dashspace.fun.car_rental_server.verification.request;

import dashspace.fun.car_rental_server.common.util.message.contact.Channel;

public record HostRegistrationVerificationRequest(
        String recepient,
        Channel channel
) {
}
