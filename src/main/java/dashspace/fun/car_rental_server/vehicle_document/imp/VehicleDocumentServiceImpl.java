package dashspace.fun.car_rental_server.vehicle_document.imp;

import dashspace.fun.car_rental_server.common.util.ImageStorageService;
import dashspace.fun.car_rental_server.vehicle.Vehicle;
import dashspace.fun.car_rental_server.vehicle.VehicleRepository;
import dashspace.fun.car_rental_server.vehicle_document.VehicleDocument;
import dashspace.fun.car_rental_server.vehicle_document.VehicleDocumentMapper;
import dashspace.fun.car_rental_server.vehicle_document.VehicleDocumentRepository;
import dashspace.fun.car_rental_server.vehicle_document.VehicleDocumentService;
import dashspace.fun.car_rental_server.vehicle_document.request.CreateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.vehicle_document.request.UpdateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.vehicle_document.response.VehicleDocumentDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDocumentServiceImpl implements VehicleDocumentService {

    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final VehicleRepository vehicleRepository;

    private final VehicleDocumentMapper vehicleDocumentMapper;
    private final ImageStorageService imageStorageService;

    @Override
    public VehicleDocumentDto create(CreateVehicleDocumentRequest request) {
        VehicleDocument document = createVehicleDocument(request);
        this.vehicleDocumentRepository.save(document);
        return this.vehicleDocumentMapper.toVehicleDocumentDto(document);
    }

    private VehicleDocument createVehicleDocument(CreateVehicleDocumentRequest request) {
        Vehicle vehicleRef = this.vehicleRepository.getReferenceById(
                request.vehicleId());
        String registrationUrl = this.imageStorageService.upload(request.registration());
        String inspectionUrl = this.imageStorageService.upload(request.inspection());
        String insuranceUrl = this.imageStorageService.upload(request.insurance());
        String frontImageUrl = this.imageStorageService.upload(request.frontImage());
        String backImageUrl = this.imageStorageService.upload(request.backImage());
        String leftImageUrl = this.imageStorageService.upload(request.leftImage());
        String rightImageUrl = this.imageStorageService.upload(request.rightImage());

        return VehicleDocument.builder()
                .vehicle(vehicleRef)
                .registrationUrl(registrationUrl)
                .inspectionUrl(inspectionUrl)
                .insuranceUrl(insuranceUrl)
                .frontImageUrl(frontImageUrl)
                .backImageUrl(backImageUrl)
                .leftImageUrl(leftImageUrl)
                .rightImageUrl(rightImageUrl)
                .build();
    }

    @Override
    public VehicleDocumentDto update(UpdateVehicleDocumentRequest request) {
        VehicleDocument document = buildVehicleDocumentFromUpdateRequest(request);
        this.vehicleDocumentRepository.save(document);
        return this.vehicleDocumentMapper.toVehicleDocumentDto(document);
    }

    private VehicleDocument buildVehicleDocumentFromUpdateRequest(UpdateVehicleDocumentRequest request) {
        Vehicle vehicleRef = this.vehicleRepository.getReferenceById(
                request.vehicleId());
        VehicleDocument existingDoc = this.vehicleDocumentRepository.findByVehicle(vehicleRef)
                .orElseThrow(() -> new EntityNotFoundException("vehicle_document"));
        if (request.registration() != null) {
            this.imageStorageService.destroy(existingDoc.getRegistrationUrl());
            String registrationUrl = this.imageStorageService.upload(request.registration());
            existingDoc.setRegistrationUrl(registrationUrl);
        }
        if (request.inspection() != null) {
            this.imageStorageService.destroy(existingDoc.getInspectionUrl());
            String inspectionUrl = this.imageStorageService.upload(request.inspection());
            existingDoc.setInspectionUrl(inspectionUrl);
        }
        if (request.insurance() != null) {
            this.imageStorageService.destroy(existingDoc.getInsuranceUrl());
            String insuranceUrl = this.imageStorageService.upload(request.insurance());
            existingDoc.setInsuranceUrl(insuranceUrl);
        }
        if (request.frontImage() != null) {
            this.imageStorageService.destroy(existingDoc.getFrontImageUrl());
            String frontImageUrl = this.imageStorageService.upload(request.frontImage());
            existingDoc.setFrontImageUrl(frontImageUrl);
        }
        if (request.backImage() != null) {
            this.imageStorageService.destroy(existingDoc.getBackImageUrl());
            String backImageUrl = this.imageStorageService.upload(request.backImage());
            existingDoc.setBackImageUrl(backImageUrl);
        }
        if (request.leftImage() != null) {
            this.imageStorageService.destroy(existingDoc.getLeftImageUrl());
            String leftImageUrl = this.imageStorageService.upload(request.leftImage());
            existingDoc.setLeftImageUrl(leftImageUrl);
        }
        if (request.rightImage() != null) {
            this.imageStorageService.destroy(existingDoc.getRightImageUrl());
            String rightImageUrl = this.imageStorageService.upload(request.rightImage());
            existingDoc.setRightImageUrl(rightImageUrl);
        }
        return existingDoc;
    }
}
