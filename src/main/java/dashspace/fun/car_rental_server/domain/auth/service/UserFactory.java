package dashspace.fun.car_rental_server.domain.auth.service;

import dashspace.fun.car_rental_server.domain.auth.dto.request.UserRegistrationRequest;
import dashspace.fun.car_rental_server.domain.role.constant.RoleNames;
import dashspace.fun.car_rental_server.domain.role.repository.RoleRepository;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.user.mapper.UserMapper;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    public User createUserWithRenterRole(UserRegistrationRequest request) {
        var newUser = mapper.toUser(request);
        assignRenterRole(newUser);
        return userRepository.save(newUser);
    }

    private void assignRenterRole(User user) {
        var role = roleRepository.findByName(RoleNames.RENTER.getValue())
                .orElseThrow(() -> new EntityNotFoundException("role.renter.not_found"));
        user.addRole(role);
    }
}
