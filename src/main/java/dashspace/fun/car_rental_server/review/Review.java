package dashspace.fun.car_rental_server.review;

import dashspace.fun.car_rental_server.rental.Rental;
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
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @Column(name = "rating")
    private Integer rating;
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;
}
