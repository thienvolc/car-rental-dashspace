package dashspace.fun.car_rental_server.domain.auth.dto;

import dashspace.fun.car_rental_server.domain.user.constant.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Integer id;

    private String email;

    private String phone;

    private String username;

    private UserStatus status;
}
