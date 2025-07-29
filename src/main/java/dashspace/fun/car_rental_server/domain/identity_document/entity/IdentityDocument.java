package dashspace.fun.car_rental_server.domain.identity_document.entity;

import dashspace.fun.car_rental_server.domain.identity_document.constant.IdentityDocumentStatus;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

import static dashspace.fun.car_rental_server.domain.identity_document.constant.IdentityDocumentStatus.PENDING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "identity_document")
public class IdentityDocument {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "verified_by_user_id", nullable = false)
    private User verifiedBy;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    @Column(name = "national_id_number", unique = true)
    private String nationalIdNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private IdentityDocumentStatus status = PENDING;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "id_front_image_url")
    private String idFrontImageUrl;

    @Column(name = "selfie_with_id_url")
    private String selfieWithIdUrl;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;
}
