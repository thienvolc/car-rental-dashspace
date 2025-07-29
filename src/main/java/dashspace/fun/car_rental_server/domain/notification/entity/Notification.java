package dashspace.fun.car_rental_server.domain.notification.entity;

import dashspace.fun.car_rental_server.domain.notification.constant.NotificationType;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

import static dashspace.fun.car_rental_server.domain.notification.constant.NotificationType.INFO;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User recepient;

    @Column(name = "title")
    private String title;
    @Column(name = "message")
    private String message;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "type")
    private NotificationType type = INFO;
    @Builder.Default
    @Column(name = "is_read")
    private boolean isRead = false;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
}
