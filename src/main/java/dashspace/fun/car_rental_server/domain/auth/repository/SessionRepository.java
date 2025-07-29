package dashspace.fun.car_rental_server.domain.auth.repository;

import dashspace.fun.car_rental_server.domain.auth.entity.Session;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    void deleteByUserAndIpAddress(User user, String ipAddress);

    void deleteAllByUser(User user);
}
