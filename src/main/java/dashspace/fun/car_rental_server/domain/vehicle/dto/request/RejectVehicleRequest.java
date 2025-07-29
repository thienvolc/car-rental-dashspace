package dashspace.fun.car_rental_server.domain.vehicle.dto.request;

import org.springframework.lang.Nullable;

public record RejectVehicleRequest(
        @Nullable String rejectionReason
) {}
