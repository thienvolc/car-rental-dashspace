package dashspace.fun.car_rental_server.vehicle_document;

import dashspace.fun.car_rental_server.common.ApplicationResponse;
import dashspace.fun.car_rental_server.vehicle_document.request.CreateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.vehicle_document.request.UpdateVehicleDocumentRequest;
import dashspace.fun.car_rental_server.vehicle_document.response.VehicleDocumentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
@Tag(name = "Vehicle document Management")
public class VehicleDocumentController {

    private final VehicleDocumentService vehicleDocumentService;
    private final VehicleDocumentMapper vehicleDocumentMapper;

    @Operation(summary = "Create a vehicle document")
    @PostMapping(path = "/{vehicleId}/me/document", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApplicationResponse> createDocument(
            @PathVariable Integer vehicleId,
            @NotNull @RequestPart("registration") MultipartFile registration,
            @NotNull @RequestPart("inspection") MultipartFile inspection,
            @NotNull @RequestPart("insurance") MultipartFile insurance,
            @NotNull @RequestPart("front_image") MultipartFile frontImage,
            @NotNull @RequestPart("left_image") MultipartFile leftImage,
            @NotNull @RequestPart("right_image") MultipartFile rightImage,
            @NotNull @RequestPart("back_image") MultipartFile backImage) {

        CreateVehicleDocumentRequest request =
                this.vehicleDocumentMapper.toVehicleDocumentDto(vehicleId,
                        registration, inspection, insurance, frontImage, leftImage,
                        rightImage, backImage);
        VehicleDocumentDto documentDto = this.vehicleDocumentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApplicationResponse.success(documentDto,
                        "vehicle_document.create.success"));
    }

    @Operation(summary = "Update vehicle document")
    @PutMapping(path = "/{vehicleId}/me/document", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApplicationResponse> updateDocument(
            @PathVariable Integer vehicleId,
            @RequestPart(value = "registration", required = false)
            MultipartFile registration,
            @RequestPart(value = "inspection", required = false)
            MultipartFile inspection,
            @RequestPart(value = "insurance", required = false)
            MultipartFile insurance,
            @RequestPart(value = "front_image", required = false)
            MultipartFile frontImage,
            @RequestPart(value = "left_image", required = false)
            MultipartFile leftImage,
            @RequestPart(value = "right_image", required = false)
            MultipartFile rightImage,
            @RequestPart(value = "back_image", required = false)
            MultipartFile backImage) {

        UpdateVehicleDocumentRequest request =
                this.vehicleDocumentMapper.toUpdateVehicleDocumentRequest(vehicleId,
                        registration, inspection, insurance, frontImage, leftImage,
                        rightImage, backImage);
        VehicleDocumentDto documentDto = this.vehicleDocumentService.update(request);
        return ResponseEntity.ok(ApplicationResponse.success(documentDto,
                "vehicle_document.update.success"));
    }
}
