package dashspace.fun.car_rental_server.domain.vehicle.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dashspace.fun.car_rental_server.domain.location.dto.LocationDto;
import dashspace.fun.car_rental_server.domain.vehicle.constant.FuelType;
import dashspace.fun.car_rental_server.domain.vehicle.constant.TransmissionType;

import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateVehicleRequest(
        String licensePlate,
        Integer manufactureYear,
        String brand,
        String model,
        Integer seatCount,
        FuelType fuelType,
        TransmissionType transmission,
        Double fuelConsumption,
        Double dailyRate,
        String description,
        Map<String, Object> features,
        LocationDto location
) {
}
