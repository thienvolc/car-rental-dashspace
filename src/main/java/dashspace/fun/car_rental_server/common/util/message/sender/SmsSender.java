package dashspace.fun.car_rental_server.common.util.message.sender;

import dashspace.fun.car_rental_server.common.util.message.contact.MessageContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsSender implements MessageSender {

    @Override
    public void sendRegistrationOtp(MessageContact contact, String code) {
        throw new RuntimeException("SMS Features not implemented yet");
    }

    @Override
    public void sendForgotPasswordOtp(MessageContact contact, String code) {
        throw new RuntimeException("SMS Features not implemented yet");
    }

    @Override
    public void sendHostRegistrationVerificationOtp(MessageContact contact, String code) {
        throw new RuntimeException("SMS Features not implemented yet");
    }
}
