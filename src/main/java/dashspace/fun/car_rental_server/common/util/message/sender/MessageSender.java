package dashspace.fun.car_rental_server.common.util.message.sender;

import dashspace.fun.car_rental_server.common.util.message.contact.MessageContact;

public interface MessageSender {
    void sendRegistrationOtp(MessageContact contact, String code);
    void sendForgotPasswordOtp(MessageContact contact, String code);
    void sendHostRegistrationVerificationOtp(MessageContact contact, String code);
}
