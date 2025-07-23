package dashspace.fun.car_rental_server.user.request;

import java.time.Instant;

public record DrivingLicenseCredentials(
        String licenseNumber,
        String fullName,
        Instant issueDate
) {}
