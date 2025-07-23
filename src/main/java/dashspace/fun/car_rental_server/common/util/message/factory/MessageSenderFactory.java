package dashspace.fun.car_rental_server.common.util.message.factory;

import dashspace.fun.car_rental_server.common.util.message.contact.Channel;
import dashspace.fun.car_rental_server.common.util.message.sender.EmailSender;
import dashspace.fun.car_rental_server.common.util.message.sender.MessageSender;
import dashspace.fun.car_rental_server.common.util.message.sender.SmsSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSenderFactory {

    private final EmailSender emailSender;
    private final SmsSender smsSender;

    public MessageSender getInstance(Channel channel) {
        return switch (channel) {
            case EMAIL -> emailSender;
            case SMS -> smsSender;
        };
    }
}
