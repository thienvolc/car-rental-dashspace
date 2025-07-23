package dashspace.fun.car_rental_server.vehicle.response;

import dashspace.fun.car_rental_server.location.LocationDto;
import dashspace.fun.car_rental_server.vehicle.enums.FuelType;
import dashspace.fun.car_rental_server.vehicle.enums.TransmissionType;
import dashspace.fun.car_rental_server.vehicle.enums.VehicleApprovalStatus;
import dashspace.fun.car_rental_server.vehicle.enums.VehicleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record AdminViewVehicleDto(
        @NotNull Integer id,
        @NotNull Integer ownerId,
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
) {}
