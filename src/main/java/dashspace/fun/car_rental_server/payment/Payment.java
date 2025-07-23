package dashspace.fun.car_rental_server.payment;

import dashspace.fun.car_rental_server.rental.Rental;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;
    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "payment_gateway")
    private String paymentGateway;
    @Column(name = "paid_at")
    private Instant paidAt;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;
}
