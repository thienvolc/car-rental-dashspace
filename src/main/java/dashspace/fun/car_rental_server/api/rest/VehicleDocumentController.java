package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import dashspace.fun.car_rental_server.app.service.ResponseFactory;
import dashspace.fun.car_rental_server.domain.vehicle.dto.request.CreateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.domain.vehicle.dto.request.UpdateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.domain.vehicle.service.VehicleDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
@Tag(name = "Vehicle document Management")
public class VehicleDocumentController {

    private final VehicleDocumentService service;
    private final ResponseFactory responseFactory;

    @Operation(summary = "Create a vehicle document")
    @PostMapping(path = "/{vehicleId}/vehicle-document", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createVehicleDocument(
            @PathVariable Integer vehicleId,
            @NonNull @RequestPart("registration") MultipartFile registration,
            @NonNull @RequestPart("inspection") MultipartFile inspection,
            @NonNull @RequestPart("insurance") MultipartFile insurance,
            @NonNull @RequestPart("front_image") MultipartFile frontImage,
            @NonNull @RequestPart("left_image") MultipartFile leftImage,
            @NonNull @RequestPart("right_image") MultipartFile rightImage,
            @NonNull @RequestPart("back_image") MultipartFile backImage) {

        var request = CreateVehicleDocumentRequest.builder()
                .vehicleId(vehicleId)
                .registration(registration)
                .inspection(inspection)
                .insurance(insurance)
                .frontImage(frontImage)
                .backImage(backImage)
                .leftImage(leftImage)
                .rightImage(rightImage)
                .build();
        var response = service.createVehicleDocument(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Update vehicle document")
    @PutMapping(path = "/{vehicleId}/vehicle-document", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto updateVehicleDocument(
            @PathVariable Integer vehicleId,
            @RequestPart(value = "registration", required = false) MultipartFile registration,
            @RequestPart(value = "inspection", required = false) MultipartFile inspection,
            @RequestPart(value = "insurance", required = false) MultipartFile insurance,
            @RequestPart(value = "front_image", required = false) MultipartFile frontImage,
            @RequestPart(value = "left_image", required = false) MultipartFile leftImage,
            @RequestPart(value = "right_image", required = false) MultipartFile rightImage,
            @RequestPart(value = "back_image", required = false) MultipartFile backImage) {

        var request = UpdateVehicleDocumentRequest.builder()
                .vehicleId(vehicleId)
                .registration(registration)
                .inspection(inspection)
                .insurance(insurance)
                .frontImage(frontImage)
                .backImage(backImage)
                .leftImage(leftImage)
                .rightImage(rightImage)
                .build();

        var response = service.updateVehicleDocument(request);
        return responseFactory.success(response);
    }
}
