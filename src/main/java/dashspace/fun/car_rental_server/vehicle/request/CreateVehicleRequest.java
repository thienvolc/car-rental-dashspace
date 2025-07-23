package dashspace.fun.car_rental_server.vehicle.request;

import dashspace.fun.car_rental_server.location.LocationDto;
import dashspace.fun.car_rental_server.vehicle.enums.FuelType;
import dashspace.fun.car_rental_server.vehicle.enums.TransmissionType;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record CreateVehicleRequest(
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
        @NotNull Map<String, Object> features
) {}
