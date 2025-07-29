package dashspace.fun.car_rental_server.domain.vehicle.mapper;

import dashspace.fun.car_rental_server.domain.vehicle.dto.response.VehicleDocumentDto;
import dashspace.fun.car_rental_server.domain.vehicle.entity.VehicleDocument;
import org.springframework.stereotype.Component;

@Component
public class VehicleDocumentMapper {

    public VehicleDocumentDto toDto(VehicleDocument document) {
        return VehicleDocumentDto.builder()
                .id(document.getId())
                .vehicleId(document.getVehicle().getId())
                .registrationUrl(document.getRegistrationUrl())
                .inspectionUrl(document.getInspectionUrl())
                .insuranceUrl(document.getInsuranceUrl())
                .frontImageUrl(document.getFrontImageUrl())
                .backImageUrl(document.getBackImageUrl())
                .leftImageUrl(document.getLeftImageUrl())
                .rightImageUrl(document.getRightImageUrl())
                .build();
    }
}
