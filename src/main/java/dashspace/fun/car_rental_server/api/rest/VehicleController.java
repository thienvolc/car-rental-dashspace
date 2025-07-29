package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import dashspace.fun.car_rental_server.app.service.ResponseFactory;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.vehicle.dto.request.CreateVehicleRequest;
import dashspace.fun.car_rental_server.domain.vehicle.dto.request.RejectVehicleRequest;
import dashspace.fun.car_rental_server.domain.vehicle.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "Vehicle Management")
public class VehicleController {

    private final VehicleService service;
    private final ResponseFactory responseFactory;

    @Operation(summary = "Create a vehicle")
    @PreAuthorize("hasRole('HOST')")
    @PostMapping(path = "/users/me/vehicles", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto register(@Valid CreateVehicleRequest request,
                                @AuthenticationPrincipal User currentUser,
                                @NonNull @RequestPart MultipartFile... images) {

        var response = service.register(request, images, currentUser.getId());
        return responseFactory.success(response);
    }

    @Operation(summary = "Get my vehicles")
    @PreAuthorize("hasRole('HOST')")
    @GetMapping("users/me/vehicles/all")
    public ResponseDto getAllOwnedVehicles(@AuthenticationPrincipal User currentUser) {
        var response = service.getAllVehiclesByUserId(currentUser.getId());
        return responseFactory.success(response);
    }

    @Operation(summary = "Get vehicle")
    @PreAuthorize("hasRole('HOST')")
    @GetMapping("users/me/vehicles/{vehicleId}")
    public ResponseDto getVehicle(@PathVariable @NonNull Integer vehicleId) {
        var response = service.getVehicleById(vehicleId);
        return responseFactory.success(response);
    }

    @Operation(summary = "Get all vehicles")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/vehicles")
    public ResponseDto getModerationViewVehicles(
            @Min(10) @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "sort", required = false) String... sortExpressions) {

        var response = service.getModerationViewVehicles(page, size, sortExpressions);
        return responseFactory.success(response);
    }

    @Operation(summary = "Get vehicle")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/vehicles/{vehicleId}")
    public ResponseDto getModerationViewVehicle(@NonNull @PathVariable Integer vehicleId) {
        var response = service.getModerationViewVehicle(vehicleId);
        return responseFactory.success(response);
    }

    @Operation(summary = "Get all vehicles")
    @GetMapping("/search")
    public ResponseDto searchVehicles(
            @Min(10) @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "sort", required = false) String... sortExpressions) {

        var response = service.searchVehicles(page, size, sortExpressions);
        return responseFactory.success(response);
    }

    @Operation(summary = "Approve vehicle")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/vehicles/{vehicleId}/approval")
    public ResponseDto approveVehicle(@PathVariable Integer vehicleId,
                                      @AuthenticationPrincipal User verifiedBy) {

        var response = service.approveVehicle(vehicleId, verifiedBy.getId());
        return responseFactory.success(response);
    }

    @Operation(summary = "Reject vehicle")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/vehicles/{vehicleId}/rejection")
    public ResponseDto rejectVehicle(@PathVariable Integer vehicleId,
                                     @Valid @RequestParam RejectVehicleRequest request,
                                     @AuthenticationPrincipal User verifiedBy) {

        var response = service.rejectVehicle(vehicleId, request, verifiedBy.getId());
        return responseFactory.success(response);
    }
}
