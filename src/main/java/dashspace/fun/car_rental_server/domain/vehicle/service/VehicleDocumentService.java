package dashspace.fun.car_rental_server.domain.vehicle.service;

import dashspace.fun.car_rental_server.domain.vehicle.dto.request.CreateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.domain.vehicle.dto.request.UpdateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.domain.vehicle.dto.response.VehicleDocumentDto;
import dashspace.fun.car_rental_server.domain.vehicle.entity.Vehicle;
import dashspace.fun.car_rental_server.domain.vehicle.entity.VehicleDocument;
import dashspace.fun.car_rental_server.domain.vehicle.mapper.VehicleDocumentMapper;
import dashspace.fun.car_rental_server.domain.vehicle.repository.VehicleDocumentRepository;
import dashspace.fun.car_rental_server.domain.vehicle.repository.VehicleRepository;
import dashspace.fun.car_rental_server.infrastructure.service.ImageStorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDocumentService {

    private final ImageStorageService imageStorageService;

    private final VehicleDocumentMapper mapper;

    private final VehicleDocumentRepository repository;
    private final VehicleRepository vehicleRepository;

    public VehicleDocumentDto createVehicleDocument(CreateVehicleDocumentRequest request) {
        var vehicleDocument = buildVehicleDocument(request);
        repository.save(vehicleDocument);
        return mapper.toDto(vehicleDocument);
    }

    private VehicleDocument buildVehicleDocument(CreateVehicleDocumentRequest request) {
        var vehicleRef = vehicleRepository.getReferenceById(request.vehicleId());

        String registrationUrl = imageStorageService.upload(request.registration());
        String inspectionUrl = imageStorageService.upload(request.inspection());
        String insuranceUrl = imageStorageService.upload(request.insurance());
        String frontImageUrl = imageStorageService.upload(request.frontImage());
        String backImageUrl = imageStorageService.upload(request.backImage());
        String leftImageUrl = imageStorageService.upload(request.leftImage());
        String rightImageUrl = imageStorageService.upload(request.rightImage());

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

    public VehicleDocumentDto updateVehicleDocument(UpdateVehicleDocumentRequest request) {
        var vehicleDocument = buildVehicleDocumentFromUpdateRequest(request);
        repository.save(vehicleDocument);
        return mapper.toDto(vehicleDocument);
    }

    private VehicleDocument buildVehicleDocumentFromUpdateRequest(UpdateVehicleDocumentRequest request) {
        var vehicleRef = vehicleRepository.getReferenceById(request.vehicleId());
        var document = tryGetVehicleDocument(vehicleRef);
        updateImagesIfExists(document, request);
        return document;
    }

    private void updateImagesIfExists(VehicleDocument document, UpdateVehicleDocumentRequest request) {
        String imageTempUrl;

        if (request.registration() != null) {
            imageStorageService.destroy(document.getRegistrationUrl());
            imageTempUrl = imageStorageService.upload(request.registration());
            document.setRegistrationUrl(imageTempUrl);
        }
        if (request.inspection() != null) {
            imageStorageService.destroy(document.getInspectionUrl());
            imageTempUrl = imageStorageService.upload(request.inspection());
            document.setInspectionUrl(imageTempUrl);
        }
        if (request.insurance() != null) {
            imageStorageService.destroy(document.getInsuranceUrl());
            imageTempUrl = imageStorageService.upload(request.insurance());
            document.setInsuranceUrl(imageTempUrl);
        }
        if (request.frontImage() != null) {
            imageStorageService.destroy(document.getFrontImageUrl());
            imageTempUrl = imageStorageService.upload(request.frontImage());
            document.setFrontImageUrl(imageTempUrl);
        }
        if (request.backImage() != null) {
            imageStorageService.destroy(document.getBackImageUrl());
            imageTempUrl = imageStorageService.upload(request.backImage());
            document.setBackImageUrl(imageTempUrl);
        }
        if (request.leftImage() != null) {
            imageStorageService.destroy(document.getLeftImageUrl());
            imageTempUrl = imageStorageService.upload(request.leftImage());
            document.setLeftImageUrl(imageTempUrl);
        }
        if (request.rightImage() != null) {
            imageStorageService.destroy(document.getRightImageUrl());
            imageTempUrl = imageStorageService.upload(request.rightImage());
            document.setRightImageUrl(imageTempUrl);
        }
    }

    private VehicleDocument tryGetVehicleDocument(Vehicle vehicleRef) {
        return repository.findByVehicle(vehicleRef)
                .orElseThrow(() -> new EntityNotFoundException("vehicle_document"));
    }
}
