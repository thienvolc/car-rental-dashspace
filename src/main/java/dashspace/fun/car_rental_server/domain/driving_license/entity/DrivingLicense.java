package dashspace.fun.car_rental_server.domain.driving_license.entity;

import dashspace.fun.car_rental_server.domain.driving_license.constant.DrivingLicenseStatus;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

import static dashspace.fun.car_rental_server.domain.driving_license.constant.DrivingLicenseStatus.PENDING;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "driving_license")
public class DrivingLicense {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @OneToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "verified_by_user_id", nullable = false)
    private User verifiedBy;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status")
    private DrivingLicenseStatus status = PENDING;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "front_image_url")
    private String frontImageUrl;

    @Column(name = "back_image_url")
    private String backImageUrl;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    @Column(name = "created_at", updatable = false, insertable = false)
    private String updatedAt;
}
