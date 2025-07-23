package dashspace.fun.car_rental_server.session;

import dashspace.fun.car_rental_server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    int deleteByUserAndIpAddress(User user, String ipAddress);
    void deleteAllByUser(User user);
}
