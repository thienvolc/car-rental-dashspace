package dashspace.fun.car_rental_server.host_document;

import dashspace.fun.car_rental_server.common.ApplicationResponse;
import dashspace.fun.car_rental_server.host_document.request.HostCredentials;
import dashspace.fun.car_rental_server.host_document.request.HostRegistrationRequest;
import dashspace.fun.car_rental_server.host_document.response.IdentityDocumentDto;
import dashspace.fun.car_rental_server.user.User;
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
@RequestMapping("/hosts")
@Tag(name = "Host Management")
public class HostController {
    private final HostService hostService;
    private final HostMapper hostMapper;

    @Operation(
            summary = "Renter register to new host",
            description = "Required valid opt code from verification service")
    @PostMapping(path = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApplicationResponse> register(
            @NotNull @RequestPart("national_id_front") MultipartFile nationalIdFront,
            @NotNull
            @RequestPart("selfie_with_national_id") MultipartFile selfieWithNationalId,
            @Valid @RequestBody HostCredentials credentials,
            @AuthenticationPrincipal User user) {

        HostRegistrationRequest request = this.hostMapper.toRegistrationRequest(
                nationalIdFront, selfieWithNationalId, credentials);
        this.hostService.register(user.getId(), request);
        ApplicationResponse response = ApplicationResponse.success(
                "host.registered.success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all host identity documents")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/identity-documents/all")
    public ResponseEntity<ApplicationResponse>
    getAllIdentityDocumentsWithSortMultipleColumns(
            @Min(10)
            @RequestParam(value = "size", defaultValue = "20", required = false)
            int pageSize,
            @RequestParam(value = "page", defaultValue = "0", required = false)
            int pageNo,
            @RequestParam(value = "sort_by", required = false) String... sortBys) {

        List<IdentityDocumentDto> response =
                this.hostService.getAllIdentityDocumentsWithSortByMultipleColumns(pageNo,
                        pageSize, sortBys);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "get.identity_document.success"));
    }

    @Operation(summary = "Get host identity document")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/identity-documents/{documentId}")
    public ResponseEntity<ApplicationResponse> getIdentityDocument(
            @PathVariable @NonNull Integer documentId) {

        IdentityDocumentDto response = this.hostService.getIdentityDocument(documentId);
        return ResponseEntity.ok(ApplicationResponse.success(response,
                "get.identity_document.success"));
    }
}
