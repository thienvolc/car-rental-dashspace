package dashspace.fun.car_rental_server.domain.role.repository;

import dashspace.fun.car_rental_server.domain.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
