package dashspace.fun.car_rental_server.vehicle;

import dashspace.fun.car_rental_server.location.Location;
import dashspace.fun.car_rental_server.rental.Rental;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.vehicle.enums.FuelType;
import dashspace.fun.car_rental_server.vehicle.enums.TransmissionType;
import dashspace.fun.car_rental_server.vehicle.enums.VehicleApprovalStatus;
import dashspace.fun.car_rental_server.vehicle.enums.VehicleStatus;
import dashspace.fun.car_rental_server.vehicle_document.VehicleDocument;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static dashspace.fun.car_rental_server.vehicle.enums.VehicleApprovalStatus.PENDING;
import static dashspace.fun.car_rental_server.vehicle.enums.VehicleStatus.ACTIVE;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "vehicle")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "verified_by_user_id", nullable = false)
    private User verifiedBy;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;
    @Column(name = "manufacture_year")
    private Integer manufactureYear;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "seat_count")
    private Integer seatCount;
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;
    @Enumerated(EnumType.STRING)
    @Column(name = "transmission")
    private TransmissionType transmission;
    @Column(name = "fuel_consumption")
    private BigDecimal fuelConsumption;
    @Column(name = "daily_rate", nullable = false)
    private BigDecimal dailyRate;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "features", columnDefinition = "JSON")
    private Map<String, Object> features;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "approval_status")
    private VehicleApprovalStatus approvalStatus = PENDING;
    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status")
    private VehicleStatus status = ACTIVE;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;

    // == Relationships ==
    @OneToMany(
            mappedBy = "vehicle",
            cascade = {PERSIST, MERGE, REMOVE},
            fetch = LAZY,
            orphanRemoval = true
    )
    private List<VehicleImage> images;
    @OneToOne(
            mappedBy = "vehicle",
            cascade = {PERSIST, MERGE, REMOVE},
            orphanRemoval = true
    )
    private VehicleDocument vehicleDocument;
    @OneToMany(mappedBy = "vehicle", fetch = LAZY)
    private List<Rental> rentals;
}
