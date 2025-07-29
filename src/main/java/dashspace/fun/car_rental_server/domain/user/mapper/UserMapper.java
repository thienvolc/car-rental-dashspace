package dashspace.fun.car_rental_server.domain.user.mapper;

import dashspace.fun.car_rental_server.domain.auth.dto.UserDto;
import dashspace.fun.car_rental_server.domain.auth.dto.request.UserRegistrationRequest;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(UserRegistrationRequest request) {
        return User.builder()
                .email(request.email())
                .username(request.email())
                .passwordHash(this.passwordEncoder.encode(request.password()))
                .build();
    }

    public UserDto toResponse(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .status(user.getStatus())
                .build();
    }
}
