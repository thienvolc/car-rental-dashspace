package dashspace.fun.car_rental_server.domain.location.mapper;

import dashspace.fun.car_rental_server.domain.location.dto.LocationDto;
import dashspace.fun.car_rental_server.domain.location.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public Location toLocation(LocationDto location) {
        return Location.builder()
                .province(location.province())
                .district(location.district())
                .ward(location.ward())
                .longitude(location.longitude())
                .latitude(location.latitude())
                .addressDetail(location.addressDetail())
                .build();
    }

    public LocationDto toDto(Location location) {
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
