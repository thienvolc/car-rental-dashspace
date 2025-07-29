package dashspace.fun.car_rental_server.domain.role.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleNames {

    ADMIN("ADMIN"), HOST("HOST"), RENTER("RENTER");

    private final String value;
}
