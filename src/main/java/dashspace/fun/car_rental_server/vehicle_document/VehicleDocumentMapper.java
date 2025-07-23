package dashspace.fun.car_rental_server.vehicle_document;

import dashspace.fun.car_rental_server.vehicle_document.request.CreateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.vehicle_document.request.UpdateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.vehicle_document.response.VehicleDocumentDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class VehicleDocumentMapper {

    public CreateVehicleDocumentRequest toVehicleDocumentDto(
            Integer vehicleId, MultipartFile registration, MultipartFile inspection,
            MultipartFile insurance, MultipartFile frontImage, MultipartFile leftImage,
            MultipartFile rightImage, MultipartFile backImage) {

        return CreateVehicleDocumentRequest.builder()
                .vehicleId(vehicleId)
                .registration(registration)
                .inspection(inspection)
                .insurance(insurance)
                .frontImage(frontImage)
                .backImage(backImage)
                .leftImage(leftImage)
                .rightImage(rightImage)
                .build();
    }

    public VehicleDocumentDto toVehicleDocumentDto(VehicleDocument document) {
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

    public UpdateVehicleDocumentRequest toUpdateVehicleDocumentRequest(
            Integer vehicleId, MultipartFile registration, MultipartFile inspection,
            MultipartFile insurance, MultipartFile frontImage, MultipartFile leftImage,
            MultipartFile rightImage, MultipartFile backImage) {

        return UpdateVehicleDocumentRequest.builder()
                .vehicleId(vehicleId)
                .registration(registration)
                .inspection(inspection)
                .insurance(insurance)
                .frontImage(frontImage)
                .backImage(backImage)
                .leftImage(leftImage)
                .rightImage(rightImage)
                .build();
    }
}
