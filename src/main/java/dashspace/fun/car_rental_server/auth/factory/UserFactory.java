package dashspace.fun.car_rental_server.auth.factory;

import dashspace.fun.car_rental_server.auth.request.RenterRegistrationRequest;
import dashspace.fun.car_rental_server.role.Role;
import dashspace.fun.car_rental_server.role.RoleName;
import dashspace.fun.car_rental_server.role.RoleRepository;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.user.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFactory {

    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public User createUserWithRoleRenter(RenterRegistrationRequest request) {
        User newUser = this.userMapper.toUser(request);
        assignRoleToUser(newUser, RoleName.RENTER.name());
        return newUser;
    }

    private void assignRoleToUser(User user, String roleName) {
        Role role = this.roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "role." + roleName.toLowerCase() + ".not_found"));

        user.addRole(role);
    }
}
