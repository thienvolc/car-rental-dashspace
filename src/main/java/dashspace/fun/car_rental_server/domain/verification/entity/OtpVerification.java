package dashspace.fun.car_rental_server.domain.verification.entity;

import dashspace.fun.car_rental_server.domain.verification.constant.VerificationPurpose;
import dashspace.fun.car_rental_server.domain.verification.constant.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

import static dashspace.fun.car_rental_server.domain.verification.constant.VerificationStatus.PENDING;
import static dashspace.fun.car_rental_server.domain.verification.constant.VerificationStatus.VERIFIED;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "otp_verification")
public class OtpVerification {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;
    @Column(name = "code")
    private String code;
    @Column(nullable = false)
    private String email;
    @Column(name = "purpose")
    @Enumerated(EnumType.STRING)
    private VerificationPurpose purpose;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status")
    private VerificationStatus status = PENDING;
    @Column(name = "expires_at", updatable = false)
    private Instant expiresAt;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    public boolean isVerified() {
        return status == VERIFIED;
    }
}
