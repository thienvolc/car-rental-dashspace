package dashspace.fun.car_rental_server.vehicle_document;

import dashspace.fun.car_rental_server.vehicle.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleDocumentRepository extends CrudRepository<VehicleDocument, Integer> {
    Optional<VehicleDocument> findByVehicle(Vehicle vehicleRef);
}
