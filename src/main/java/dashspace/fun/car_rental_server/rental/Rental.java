package dashspace.fun.car_rental_server.rental;

import dashspace.fun.car_rental_server.payment.Payment;
import dashspace.fun.car_rental_server.review.Review;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static dashspace.fun.car_rental_server.rental.RentalStatus.PENDING;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;
    @Column(name = "rental_code")
    private String rentalCode;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "approved_by_user_id")
    private User approvedBy;

    @Column(name = "pickup_date")
    private Instant pickupDate;
    @Column(name = "return_date")
    private Instant returnDate;
    @Column(name = "actual_return_date")
    private Instant actualReturnDate;
    @Column(name = "daily_rate")
    private BigDecimal dailyRate;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status")
    private RentalStatus status = PENDING;
    @Column(name = "approved_at")
    private Instant approvedAt;
    @Column(name = "pickup_location")
    private String pickupLocation;
    @Column(name = "return_location")
    private String returnLocation;
    @Column(name = "additional_charges")
    private Double additionalCharges;
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;

    // == Relationships ==
    @OneToMany(
            mappedBy = "rental",
            cascade = {PERSIST, MERGE},
            fetch = LAZY
    )
    private List<Payment> payment;
    @OneToOne(
            mappedBy = "rental",
            cascade = {PERSIST, MERGE, REMOVE},
            orphanRemoval = true
    )
    private RentalCancellation cancellation;
    @OneToOne(
            mappedBy = "rental",
            cascade = {PERSIST, MERGE, REMOVE},
            orphanRemoval = true
    )
    private Review review;
}
