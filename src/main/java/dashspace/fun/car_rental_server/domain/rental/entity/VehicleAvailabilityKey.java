package dashspace.fun.car_rental_server.domain.rental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class VehicleAvailabilityKey {

    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @Column(name = "date")
    private LocalDate date;
}
