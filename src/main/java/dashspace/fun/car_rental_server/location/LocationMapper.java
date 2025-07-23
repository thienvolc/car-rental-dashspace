package dashspace.fun.car_rental_server.location;

import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.vehicle.Vehicle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationMapper {
    public Location toLocation(User ownerRef, Vehicle vehicleRef, LocationDto location) {
        return Location.builder()
                .owner(ownerRef)
                .vehicles(List.of(vehicleRef))
                .province(location.province())
                .district(location.district())
                .ward(location.ward())
                .longitude(location.longitude())
                .latitude(location.latitude())
                .addressDetail(location.addressDetail())
                .build();
    }

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .addressDetail(location.getAddressDetail())
                .ward(location.getWard())
                .province(location.getProvince())
                .district(location.getDistrict())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .build();
    }
}
