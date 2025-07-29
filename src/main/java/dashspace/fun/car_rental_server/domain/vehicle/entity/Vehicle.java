package dashspace.fun.car_rental_server.domain.vehicle.entity;

import dashspace.fun.car_rental_server.domain.location.entity.Location;
import dashspace.fun.car_rental_server.domain.rental.entity.Rental;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.vehicle.constant.FuelType;
import dashspace.fun.car_rental_server.domain.vehicle.constant.TransmissionType;
import dashspace.fun.car_rental_server.domain.vehicle.constant.VehicleApprovalStatus;
import dashspace.fun.car_rental_server.domain.vehicle.constant.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "vehicle")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by_user_id", nullable = false)
    private User verifiedBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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
    private VehicleApprovalStatus approvalStatus = VehicleApprovalStatus.PENDING;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status")
    private VehicleStatus status = VehicleStatus.ACTIVE;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;

    // == Relationships ==
    @OneToMany(
            mappedBy = "vehicle",
            cascade = {PERSIST, MERGE, REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<VehicleImage> images;

    @OneToOne(
            mappedBy = "vehicle",
            cascade = {PERSIST, MERGE, REMOVE},
            orphanRemoval = true
    )
    private VehicleDocument vehicleDocument;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<Rental> rentals;
}
