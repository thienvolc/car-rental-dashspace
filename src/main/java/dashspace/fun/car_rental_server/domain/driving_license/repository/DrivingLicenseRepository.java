package dashspace.fun.car_rental_server.domain.driving_license.repository;

import dashspace.fun.car_rental_server.domain.driving_license.entity.DrivingLicense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DrivingLicenseRepository extends CrudRepository<DrivingLicense, Integer> {
    Page<DrivingLicense> findAll(Pageable pageable);
}
