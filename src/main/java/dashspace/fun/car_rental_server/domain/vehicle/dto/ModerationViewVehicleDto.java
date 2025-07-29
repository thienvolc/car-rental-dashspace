package dashspace.fun.car_rental_server.domain.vehicle.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dashspace.fun.car_rental_server.domain.location.dto.LocationDto;
import dashspace.fun.car_rental_server.domain.vehicle.constant.FuelType;
import dashspace.fun.car_rental_server.domain.vehicle.constant.TransmissionType;
import dashspace.fun.car_rental_server.domain.vehicle.constant.VehicleApprovalStatus;
import dashspace.fun.car_rental_server.domain.vehicle.constant.VehicleStatus;
import dashspace.fun.car_rental_server.domain.vehicle.dto.response.VehicleDocumentDto;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ModerationViewVehicleDto(
        Integer id,
        Integer ownerId,
        LocationDto location,
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
        VehicleApprovalStatus approvalStatus,
        VehicleStatus status,
        List<VehicleImageDto> images,
        VehicleDocumentDto document
) {
}
