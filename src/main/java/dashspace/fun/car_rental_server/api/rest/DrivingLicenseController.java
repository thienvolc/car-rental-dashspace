package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import dashspace.fun.car_rental_server.app.service.ResponseFactory;
import dashspace.fun.car_rental_server.domain.driving_license.dto.request.CreateDrivingLicenseRequest;
import dashspace.fun.car_rental_server.domain.driving_license.dto.request.DrivingLicenseCredentials;
import dashspace.fun.car_rental_server.domain.driving_license.dto.request.RejectDrivingLicenseRequest;
import dashspace.fun.car_rental_server.domain.driving_license.service.DrivingLicenseService;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "Driving licenses management")
public class DrivingLicenseController {

    private final DrivingLicenseService service;
    private final ResponseFactory responseFactory;

    @Operation(summary = "Create a driving license")
    @PutMapping("users/me/driving-licenses")
    public ResponseDto createDrivingLicense(
            @NonNull @RequestPart("front_image") MultipartFile frontImage,
            @NonNull @RequestPart("back_image") MultipartFile backImage,
            @Valid @RequestBody DrivingLicenseCredentials credentials,
            @AuthenticationPrincipal User currentUser) {

        var request = CreateDrivingLicenseRequest.builder()
                .userId(currentUser.getId())
                .licenseNumber(credentials.licenseNumber())
                .issueDate(credentials.issueDate())
                .expiryDate(credentials.expiryDate())
                .fullName(credentials.fullName())
                .frontImage(frontImage)
                .backImage(backImage)
                .build();

        var response = service.createDrivingLicense(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Get user driving license")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/driving-licenses")
    public ResponseDto getAllDrivingLicenses(
            @Min(10) @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page) {

        var response = service.getAllDrivingLicenses(page, size);
        return responseFactory.success(response);
    }

    @Operation(summary = "Get user driving license")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/driving-licenses/{drivingLicenseId}")
    public ResponseDto getDrivingLicense(@PathVariable @NonNull Integer drivingLicenseId) {
        var response = service.getDrivingLicenseById(drivingLicenseId);
        return responseFactory.success(response);
    }

    @Operation(summary = "Approve a driving license")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/driving-licenses/{drivingLicenseId}/approval")
    public ResponseDto approveDrivingLicense(@PathVariable Integer drivingLicenseId,
                                             @AuthenticationPrincipal User verifiedBy) {

        var response = service.approveDrivingLicense(drivingLicenseId, verifiedBy.getId());
        return responseFactory.success(response);
    }

    @Operation(summary = "Reject a driving license")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/driving-licenses/{drivingLicenseId}/reject")
    public ResponseDto rejectDrivingLicense(
            @PathVariable Integer drivingLicenseId,
            @RequestParam RejectDrivingLicenseRequest request,
            @AuthenticationPrincipal User verifiedBy) {

        var response = service.rejectDrivingLicense(drivingLicenseId, request, verifiedBy.getId());
        return responseFactory.success(response);
    }
}
