package dashspace.fun.car_rental_server.rental;

import dashspace.fun.car_rental_server.user.User;
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
@Table(name = "rental_cancellation")
public class RentalCancellation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    @ManyToOne
    @JoinColumn(name = "cancelled_by_user_id", nullable = false)
    private User cancelledBy;

    @Column(name = "cancelled_at")
    private Instant cancelledAt;
    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
}
