package dashspace.fun.car_rental_server.api.rest;

import dashspace.fun.car_rental_server.app.dto.response.v2.ResponseDto;
import dashspace.fun.car_rental_server.app.service.ResponseFactory;
import dashspace.fun.car_rental_server.domain.auth.dto.request.VerificationRequest;
import dashspace.fun.car_rental_server.domain.identity_document.dto.IdentityDocumentImages;
import dashspace.fun.car_rental_server.domain.identity_document.dto.request.HostRegistrationRequest;
import dashspace.fun.car_rental_server.domain.identity_document.dto.request.IdentityDocumentRejectionRequest;
import dashspace.fun.car_rental_server.domain.identity_document.service.HostRegistrationVerificationService;
import dashspace.fun.car_rental_server.domain.identity_document.service.IdentityDocumentService;
import dashspace.fun.car_rental_server.domain.user.entity.User;
import dashspace.fun.car_rental_server.domain.verification.dto.request.VerifyOtpRequest;
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
@Tag(name = "Host Management")
@RequestMapping("/identity-documents")
public class IdentityDocumentController {

    private final IdentityDocumentService service;
    private final HostRegistrationVerificationService verificationService;

    private final ResponseFactory responseFactory;

    @Operation(summary = "Send OTP for host registration")
    @PostMapping("/register/verifications")
    public ResponseDto sendHostRegistrationOtp(@Valid @RequestBody VerificationRequest request,
                                               @AuthenticationPrincipal User currentUser) {

        var response = verificationService.sendHostRegistrationOtp(request, currentUser.getId());
        return responseFactory.success(response);
    }

    @Operation(summary = "Verify OTP for host registration")
    @PatchMapping("/register/verifications")
    public ResponseDto verifyHostRegistrationOtp(@Valid @RequestBody VerifyOtpRequest request) {
        var response = verificationService.verifyHostRegistrationOtp(request);
        return responseFactory.success(response);
    }

    @Operation(summary = "Renter register to new host. Required valid opt code")
    @PostMapping(path = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto register(
            @NonNull @RequestPart("national_id_front") MultipartFile nationalIdFront,
            @NonNull @RequestPart("selfie_with_national_id") MultipartFile selfieWithNationalId,
            @Valid @RequestBody HostRegistrationRequest request,
            @AuthenticationPrincipal User currentUser) {

        var documentImages = IdentityDocumentImages.builder()
                .nationalIdFront(nationalIdFront)
                .selfieWithNationalId(selfieWithNationalId)
                .build();
        var response = service.register(request, documentImages, currentUser.getId());
        return responseFactory.success(response);
    }

    @Operation(summary = "Get all host identity documents")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseDto getIdentityDocuments(
            @Min(10) @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "sort", required = false) String... sortExpressions) {

        var response = service.getIdentityDocuments(page, size, sortExpressions);
        return responseFactory.success(response);
    }

    @Operation(summary = "Get host identity document")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{identityDocumentId}")
    public ResponseDto getIdentityDocument(@PathVariable Integer identityDocumentId) {
        var response = service.getIdentityDocument(identityDocumentId);
        return responseFactory.success(response);
    }

    @Operation(summary = "Approve identity document")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{identityDocumentId}/approval")
    public ResponseDto approveIdentityDocument(@PathVariable Integer identityDocumentId,
                                               @AuthenticationPrincipal User verifiedBy) {

        var response = service.approveIdentityDocument(identityDocumentId, verifiedBy.getId());
        return responseFactory.success(response);
    }

    @Operation(summary = "Reject identity document")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{identityDocumentId}/rejection")
    public ResponseDto rejectIdentityDocument(
            @PathVariable Integer identityDocumentId,
            @Valid @RequestBody IdentityDocumentRejectionRequest request,
            @AuthenticationPrincipal User verifiedBy) {

        var response = service.rejectIdentityDocument(identityDocumentId, request, verifiedBy.getId());
        return responseFactory.success(response);
    }
}
