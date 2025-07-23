package dashspace.fun.car_rental_server.session.request;

import dashspace.fun.car_rental_server.auth.request.ClientDeviceInfomation;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SessionCommand {
    private Integer userId;
    private ClientDeviceInfomation deviceInfo;
    private String refreshToken;
}
