package dashspace.fun.car_rental_server.vehicle.response;

import dashspace.fun.car_rental_server.location.LocationDto;
import dashspace.fun.car_rental_server.vehicle.enums.FuelType;
import dashspace.fun.car_rental_server.vehicle.enums.TransmissionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record SearchVehicleDto(
        @NotNull Integer id,
        @NotNull VehicleOwnerDto owner,
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
        @NotNull List<VehicleImageDto> images
) {}
