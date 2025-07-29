package dashspace.fun.car_rental_server.domain.vehicle.repository;

import dashspace.fun.car_rental_server.domain.vehicle.entity.Vehicle;
import dashspace.fun.car_rental_server.domain.vehicle.entity.VehicleDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleDocumentRepository extends CrudRepository<VehicleDocument, Integer> {
    Optional<VehicleDocument> findByVehicle(Vehicle vehicleRef);
}
