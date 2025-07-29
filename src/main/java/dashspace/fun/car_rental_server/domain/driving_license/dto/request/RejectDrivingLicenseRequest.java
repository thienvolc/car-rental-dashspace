package dashspace.fun.car_rental_server.domain.driving_license.dto.request;

import jakarta.validation.constraints.NotNull;

public record RejectDrivingLicenseRequest(
        @NotNull String rejectionReason
) {
}
