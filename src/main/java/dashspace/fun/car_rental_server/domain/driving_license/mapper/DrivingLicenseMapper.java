package dashspace.fun.car_rental_server.domain.driving_license.mapper;

import dashspace.fun.car_rental_server.domain.driving_license.dto.DrivingLicenseDto;
import dashspace.fun.car_rental_server.domain.driving_license.dto.request.CreateDrivingLicenseRequest;
import dashspace.fun.car_rental_server.domain.driving_license.entity.DrivingLicense;
import org.springframework.stereotype.Service;

@Service
public class DrivingLicenseMapper {

    public DrivingLicense toDrivingLicense(CreateDrivingLicenseRequest request) {
        return DrivingLicense.builder()
                .licenseNumber(request.licenseNumber())
                .fullName(request.fullName())
                .issueDate(request.issueDate())
                .expiryDate(request.expiryDate())
                .build();
    }

    public DrivingLicenseDto toDto(DrivingLicense drivingLicense) {
        return DrivingLicenseDto.builder()
                .fullName(drivingLicense.getFullName())
                .licenseNumber(drivingLicense.getLicenseNumber())
                .issueDate(drivingLicense.getIssueDate())
                .expiryDate(drivingLicense.getExpiryDate())
                .status(drivingLicense.getStatus())
                .backImageUrl(drivingLicense.getBackImageUrl())
                .frontImageUrl(drivingLicense.getFrontImageUrl())
                .build();
    }
}
