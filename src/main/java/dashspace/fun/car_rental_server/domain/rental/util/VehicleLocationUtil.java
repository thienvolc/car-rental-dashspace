package dashspace.fun.car_rental_server.domain.rental.util;

import dashspace.fun.car_rental_server.domain.location.entity.Location;

public class VehicleLocationUtil {

    public static String extract(Location location) {
        return "%s, %s, %s, %s, %s, %s".formatted(
                location.getLatitude(),
                location.getLongitude(),
                location.getProvince(),
                location.getDistrict(),
                location.getWard(),
                location.getAddressDetail()
        );
    }
}
