package dashspace.fun.car_rental_server.domain.user.entity;

import dashspace.fun.car_rental_server.domain.driving_license.entity.DrivingLicense;
import dashspace.fun.car_rental_server.domain.auth.entity.Session;
import dashspace.fun.car_rental_server.domain.location.entity.Location;
import dashspace.fun.car_rental_server.domain.notification.entity.Notification;
import dashspace.fun.car_rental_server.domain.rental.entity.Rental;
import dashspace.fun.car_rental_server.domain.rental.constant.RentalCancellation;
import dashspace.fun.car_rental_server.domain.rental.entity.Review;
import dashspace.fun.car_rental_server.domain.role.entity.Role;
import dashspace.fun.car_rental_server.domain.user.constant.Gender;
import dashspace.fun.car_rental_server.domain.user.constant.UserStatus;
import dashspace.fun.car_rental_server.domain.identity_document.entity.IdentityDocument;
import dashspace.fun.car_rental_server.domain.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static dashspace.fun.car_rental_server.domain.user.constant.UserStatus.*;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "email_verified")
    @Builder.Default
    private boolean emailVerified = false;
    @Column(name = "phone_verified")
    @Builder.Default
    private boolean phoneVerified = false;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = ACTIVE;
    @Column(name = "last_login_at")
    private Instant lastLoginAt;
    @Column(name = "password_changed_at")
    private Instant passwordChangedAt;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;
    @Column(name = "updated_at", updatable = false, insertable = false)
    private Instant updatedAt;

    // == Relationships ==
    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;

    @OneToMany(
            mappedBy = "user",
            cascade = REMOVE,
            fetch = LAZY,
            orphanRemoval = true
    )
    private List<Session> sessions;
    @OneToOne(mappedBy = "owner", cascade = {PERSIST, MERGE})
    private DrivingLicense ownedDrivingLicense;
    @OneToOne(mappedBy = "owner", cascade = {PERSIST, MERGE})
    private IdentityDocument ownedIdentityDocument;
    @OneToMany(mappedBy = "owner", cascade = {PERSIST, MERGE, REMOVE}, fetch = LAZY)
    private List<Location> locations;
    @OneToMany(mappedBy = "owner", cascade = {PERSIST, MERGE}, fetch = LAZY)
    private List<Vehicle> ownedVehicles;
    @OneToMany(mappedBy = "recepient", cascade = REMOVE, fetch = LAZY)
    private List<Notification> notifications;

    // == Rental ==
    @OneToMany(mappedBy = "renter", fetch = LAZY)
    private List<Rental> rentals;
    @OneToMany(mappedBy = "approvedBy", fetch = LAZY)
    private List<Rental> approvedRentals;
    @OneToMany(mappedBy = "cancelledBy", fetch = LAZY)
    private List<RentalCancellation> rentalCancellations;
    @OneToMany(mappedBy = "reviewer", fetch = LAZY)
    private List<Review> reviews;

    // == Verification ==
    @OneToMany(mappedBy = "verifiedBy", fetch = LAZY)
    private List<DrivingLicense> verifiedDrivingLicenses;
    @OneToMany(mappedBy = "verifiedBy", fetch = LAZY)
    private List<IdentityDocument> verifiedIdentityDocuments;
    @OneToMany(mappedBy = "verifiedBy", fetch = LAZY)
    private List<Vehicle> verifiedVehicles;

    // == UserDetails ==
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(roles))
            return List.of();
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != BANNED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != INACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return status == ACTIVE;
    }

    // == Helper Methods ==
    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }

        this.roles.add(role);
    }
}
