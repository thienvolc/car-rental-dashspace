package dashspace.fun.car_rental_server.domain.vehicle.repository;

import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.vehicle.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    boolean existsByLicensePlate(String licensePlate);

    @EntityGraph(attributePaths = {"images", "location", "vehicleDocument"})
    List<Vehicle> findAllByOwner(User owner);

    @EntityGraph(attributePaths = {"owner", "images", "location", "vehicleDocument"})
    Optional<Vehicle> findFullById(Integer id);

    @EntityGraph(attributePaths = {"owner", "images", "location", "vehicleDocument"})
    Page<Vehicle> findFullAll(Pageable pageable);

    boolean existsByOwnerAndId(User owner, Integer id);
}
