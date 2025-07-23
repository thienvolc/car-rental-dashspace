package dashspace.fun.car_rental_server.vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
    Vehicle getReferenceById(Integer vehicleId);

    boolean existsByLicensePlate(String licensePlate);

    @SuppressWarnings("NullableProblems")
    @EntityGraph(attributePaths = {"owner", "images", "location"})
    Optional<Vehicle> findById(Integer id);

    @EntityGraph(attributePaths = {"owner", "images", "location"})
    Page<Vehicle> findAll(Pageable pageable);
}
