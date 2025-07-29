package dashspace.fun.car_rental_server.domain.location.service;

import dashspace.fun.car_rental_server.domain.location.dto.LocationDto;
import dashspace.fun.car_rental_server.domain.location.entity.Location;
import dashspace.fun.car_rental_server.domain.location.mapper.LocationMapper;
import dashspace.fun.car_rental_server.domain.location.repository.LocationRepository;
import dashspace.fun.car_rental_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationMapper mapper;

    private final UserRepository userRepository;
    private final LocationRepository repository;

    public Location createLocation(LocationDto locationDto, Integer userId) {
        var userRef = userRepository.getReferenceById(userId);
        var location = mapper.toLocation(locationDto);
        location.setOwner(userRef);

        return repository.save(location);
    }
}
