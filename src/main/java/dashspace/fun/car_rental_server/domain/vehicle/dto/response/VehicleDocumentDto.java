package dashspace.fun.car_rental_server.domain.vehicle.dto.response;

import lombok.Builder;

@Builder
public record VehicleDocumentDto(
        Integer id,
        Integer vehicleId,
        String registrationUrl,
        String inspectionUrl,
        String insuranceUrl,
        String frontImageUrl,
        String leftImageUrl,
        String rightImageUrl,
        String backImageUrl
) {
}
