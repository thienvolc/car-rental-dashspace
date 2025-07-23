package dashspace.fun.car_rental_server.common.util.message.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageContact {
    private String recipient;
    private Channel channel;

    public static MessageContact fromEmail(String email) {
        return new MessageContact(email, Channel.EMAIL);
    }
}
