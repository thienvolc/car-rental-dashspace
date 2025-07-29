package dashspace.fun.car_rental_server.domain.driving_license.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DrivingLicenseCredentials(
        String licenseNumber,
        String fullName,
        LocalDate issueDate,
        LocalDate expiryDate
) {
}
