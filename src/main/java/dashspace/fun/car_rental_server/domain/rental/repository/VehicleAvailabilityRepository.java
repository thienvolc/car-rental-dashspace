package dashspace.fun.car_rental_server.domain.rental.repository;

import dashspace.fun.car_rental_server.domain.rental.entity.VehicleAvailability;
import dashspace.fun.car_rental_server.domain.rental.entity.VehicleAvailabilityKey;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VehicleAvailabilityRepository extends CrudRepository<VehicleAvailability, VehicleAvailabilityKey> {

    @Query("""
            SELECT va
            FROM VehicleAvailability va
            WHERE va.key.vehicleId = :vehicleId
              AND va.status = 'AVAILABLE'
              AND va.key.date BETWEEN :pickupDate And :returnDate
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<VehicleAvailability> findAndLockVehicleAvailabilities(@Param("vehicleId") Integer vehicleId,
                                                               @Param("pickupDate") LocalDate pickupDate,
                                                               @Param("returnDate") LocalDate returnDate);

    @Query("""
            SELECT va
            FROM VehicleAvailability va
            WHERE va.key.vehicleId = :vehicleId
              AND va.key.date BETWEEN :pickupDate And :returnDate
            """)
    List<VehicleAvailability> findRange(@Param("vehicleId") Integer vehicleId,
                                                        @Param("pickupDate") LocalDate pickupDate,
                                                        @Param("returnDate") LocalDate returnDate);
}
