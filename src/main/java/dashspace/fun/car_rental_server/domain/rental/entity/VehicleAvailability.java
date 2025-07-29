package dashspace.fun.car_rental_server.domain.rental.entity;

import dashspace.fun.car_rental_server.domain.rental.constant.VehicleAvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vehicle_availability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleAvailability {
    @EmbeddedId
    private VehicleAvailabilityKey key;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private VehicleAvailabilityStatus status;
}
