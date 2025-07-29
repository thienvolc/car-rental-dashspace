package dashspace.fun.car_rental_server.domain.location.repository;

import dashspace.fun.car_rental_server.domain.location.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {
}
