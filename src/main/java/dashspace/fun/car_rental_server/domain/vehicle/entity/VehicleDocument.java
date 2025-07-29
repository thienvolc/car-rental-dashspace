package dashspace.fun.car_rental_server.domain.vehicle.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vehicle_document")
public class VehicleDocument {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @OneToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    private Vehicle vehicle;

    @Column(name = "registration_url")
    private String registrationUrl;
    @Column(name = "inspection_url")
    private String inspectionUrl;
    @Column(name = "insurance_url")
    private String insuranceUrl;
    @Column(name = "front_image_url")
    private String frontImageUrl;
    @Column(name = "left_image_url")
    private String leftImageUrl;
    @Column(name = "right_image_url")
    private String rightImageUrl;
    @Column(name = "back_image_url")
    private String backImageUrl;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;
}
