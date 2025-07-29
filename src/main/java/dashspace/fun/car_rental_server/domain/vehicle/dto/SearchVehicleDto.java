package dashspace.fun.car_rental_server.domain.vehicle.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dashspace.fun.car_rental_server.domain.location.dto.LocationDto;
import dashspace.fun.car_rental_server.domain.vehicle.constant.FuelType;
import dashspace.fun.car_rental_server.domain.vehicle.constant.TransmissionType;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SearchVehicleDto(
        Integer id,
        VehicleOwnerDto owner,
        LocationDto location,
        String licensePlate,
        Integer manufactureYear,
        String brand,
        String model,
        Integer seatCount,
        FuelType fuelType,
        TransmissionType transmission,
        double fuelConsumption,
        double dailyRate,
        String description,
        Map<String, Object> features,
        List<VehicleImageDto> images
) {
}
