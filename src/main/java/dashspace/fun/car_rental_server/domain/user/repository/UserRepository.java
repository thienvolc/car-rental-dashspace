package dashspace.fun.car_rental_server.domain.user.repository;

import dashspace.fun.car_rental_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByEmailAndId(String email, Integer id);
}
