package dashspace.fun.car_rental_server.user.response;

import dashspace.fun.car_rental_server.host_document.enums.DrivingLicenseStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record DrivingLicenseDto(
        Integer id,
        String licenseNumber,
        String fullName,
        Instant issueDate,
        Instant expiryDate,
        DrivingLicenseStatus status,
        String rejectionReason,
        String frontImageUrl,
        String backImageUrl
) {}
