package dashspace.fun.car_rental_server.domain.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Locale {
    VIETNAM("vn"),
    ENGLISH("en");

    private final String code;
}
