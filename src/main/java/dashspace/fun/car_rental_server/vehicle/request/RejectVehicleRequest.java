package dashspace.fun.car_rental_server.vehicle.request;

import org.springframework.lang.Nullable;

public record RejectVehicleRequest(
        @Nullable String rejectionReason
) {}
