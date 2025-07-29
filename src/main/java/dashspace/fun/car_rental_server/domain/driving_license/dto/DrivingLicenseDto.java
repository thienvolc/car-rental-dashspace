package dashspace.fun.car_rental_server.domain.driving_license.dto;

import dashspace.fun.car_rental_server.domain.driving_license.constant.DrivingLicenseStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DrivingLicenseDto(
        Integer id,
        String licenseNumber,
        String fullName,
        LocalDate issueDate,
        LocalDate expiryDate,
        DrivingLicenseStatus status,
        String rejectionReason,
        String frontImageUrl,
        String backImageUrl
) {
}
