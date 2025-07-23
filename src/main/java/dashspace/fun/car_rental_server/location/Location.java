package dashspace.fun.car_rental_server.location;

import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Column(name = "province")
    private String province;
    @Column(name = "district")
    private String district;
    @Column(name = "ward")
    private String ward;
    @Column(name = "address_detail", columnDefinition = "TEXT")
    private String addressDetail;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;

    // == Relationships ==
    @OneToMany(mappedBy = "location", fetch = LAZY)
    private List<Vehicle> vehicles;
}
