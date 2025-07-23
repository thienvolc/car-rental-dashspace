package dashspace.fun.car_rental_server.vehicle;

import dashspace.fun.car_rental_server.location.LocationDto;
import dashspace.fun.car_rental_server.vehicle.enums.FuelType;
import dashspace.fun.car_rental_server.vehicle.enums.TransmissionType;
import dashspace.fun.car_rental_server.vehicle.enums.VehicleApprovalStatus;
import dashspace.fun.car_rental_server.vehicle.enums.VehicleStatus;
import dashspace.fun.car_rental_server.vehicle.response.VehicleImageDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record HostVehicleDto(
        @NotNull Integer id,
        @NotNull LocationDto location,
        @NotNull String licensePlate,
        @NotNull Integer manufactureYear,
        @NotNull String brand,
        @NotNull String model,
        @NotNull Integer seatCount,
        @NotNull FuelType fuelType,
        @NotNull TransmissionType transmission,
        @NotNull double fuelConsumption,
        @NotNull double dailyRate,
        @NotNull String description,
        @NotNull Map<String, Object> features,
        @NotNull List<VehicleImageDto> images,
        @NotNull VehicleApprovalStatus approvalStatus,
        @NotNull VehicleStatus status
) {
}
