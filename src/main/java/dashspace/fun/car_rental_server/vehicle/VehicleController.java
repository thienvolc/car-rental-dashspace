package dashspace.fun.car_rental_server.vehicle;

import dashspace.fun.car_rental_server.common.ApplicationResponse;
import dashspace.fun.car_rental_server.user.User;
import dashspace.fun.car_rental_server.vehicle.request.CreateVehicleRequest;
import dashspace.fun.car_rental_server.vehicle.request.RejectVehicleRequest;
import dashspace.fun.car_rental_server.vehicle.response.AdminViewVehicleDto;
import dashspace.fun.car_rental_server.vehicle.response.SearchVehicleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
@Tag(name = "Vehicle Management")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Create a new vehicle")
    @PreAuthorize("hasRole('HOST')")
    @PostMapping(path = "/me", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApplicationResponse> registerVehicle(
            @Valid CreateVehicleRequest request,
            @AuthenticationPrincipal User user,
            @NotNull @RequestPart MultipartFile... carImages) {

        this.vehicleService.registerVehicle(user.getId(), request, carImages);
        ApplicationResponse response = ApplicationResponse.success(
                "vehicle.create.success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all my vehicles")
    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/me/all")
    public ResponseEntity<ApplicationResponse> getAllHostVehicles(
            @Min(10)
            @RequestParam(value = "size", defaultValue = "20", required = false)
            int pageSize,
            @RequestParam(value = "page", defaultValue = "0", required = false)
            int pageNo) {

        List<HostVehicleDto> response = this.vehicleService.getAllHostVehicles(pageNo, pageSize);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "vehicle.get.all.success"));
    }

    @Operation(summary = "Get vehicle")
    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/{vehicleId}/me")
    public ResponseEntity<ApplicationResponse> getHostVehicle(
            @PathVariable @NotNull Integer vehicleId) {

        HostVehicleDto response = this.vehicleService.getHostVehicle(vehicleId);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "vehicle.get.success"));
    }

    @Operation(summary = "Get all vehicles")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApplicationResponse>
    getAllAdminViewVehiclesWithSortMultipleColumns(
            @Min(10)
            @RequestParam(value = "size", defaultValue = "20", required = false)
            int pageSize,
            @RequestParam(value = "page", defaultValue = "0", required = false)
            int pageNo,
            @RequestParam(value = "sort_by", required = false) String... sortBys) {

        List<AdminViewVehicleDto> response =
                this.vehicleService.getAllAdminViewVehiclesWithSortByMultipleColumns(
                        pageNo, pageSize, sortBys);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "vehicle.get.all.success"));
    }

    @Operation(summary = "Get vehicle")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{vehicleId}")
    public ResponseEntity<ApplicationResponse> getAdminViewVehicle(
            @PathVariable @NonNull Integer vehicleId) {

        AdminViewVehicleDto response = this.vehicleService.getAdminViewVehicle(
                vehicleId);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "vehicle.get.success"));
    }

    @Operation(summary = "Get all vehicles")
    @GetMapping("/search")
    public ResponseEntity<ApplicationResponse> getAllVehiclesWithSortMultipleColumns(
            @Min(10)
            @RequestParam(value = "size", defaultValue = "20", required = false)
            int pageSize,
            @RequestParam(value = "page", defaultValue = "0", required = false)
            int pageNo,
            @RequestParam(value = "sort_by", required = false) String... sortBys) {

        List<SearchVehicleDto> response =
                this.vehicleService.searchAllVehiclesWithSortByMultipleColumns(
                        pageNo, pageSize, sortBys);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "vehicle.search.success"));
    }

    @Operation(summary = "Approve vehicle")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approval/{vehicleId}")
    public ResponseEntity<ApplicationResponse> approveVehicle(
            @NotNull @PathVariable Integer vehicleId,
            @AuthenticationPrincipal User verifiedBy) {

        this.vehicleService.approveVehicle(vehicleId, verifiedBy.getId());
        return ResponseEntity.ok(ApplicationResponse.success("vehicle.approve.success"));
    }

    @Operation(summary = "Reject vehicle")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/rejection/{vehicleId}")
    public ResponseEntity<ApplicationResponse> rejectVehicle(
            @NotNull @PathVariable Integer vehicleId,
            @Valid @RequestParam RejectVehicleRequest request,
            @AuthenticationPrincipal User verifiedBy) {

        this.vehicleService.rejectVehicle(vehicleId, verifiedBy.getId(), request);
        return ResponseEntity.ok(ApplicationResponse.success("vehicle.reject.success"));
    }
}
