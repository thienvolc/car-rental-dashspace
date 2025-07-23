package dashspace.fun.car_rental_server.session;

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
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
    @Column(name = "refresh_token_expires", nullable = false)
    private Instant refreshTokenExpires;
    @Column(name = "device_info")
    private String deviceInfo;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "user_agent")
    private String userAgent;
    @Builder.Default
    @Column(name = "is_active")
    private boolean isActive = true;
    @Column(name = "last_used_at")
    private Instant lastUsedAt;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
}
