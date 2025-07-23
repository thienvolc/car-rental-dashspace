package dashspace.fun.car_rental_server.host_document;

import dashspace.fun.car_rental_server.host_document.request.HostCredentials;
import dashspace.fun.car_rental_server.host_document.request.HostRegistrationRequest;
import dashspace.fun.car_rental_server.host_document.response.IdentityDocumentDto;
import dashspace.fun.car_rental_server.user.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Optional;

@Component
public class HostMapper {
    public HostRegistrationRequest toRegistrationRequest(
            MultipartFile nationalIdFront, MultipartFile selfieWithNationalId,
            HostCredentials credentials) {

        return HostRegistrationRequest.builder()
                .credentials(credentials)
                .nationalIdFront(nationalIdFront)
                .selfieWithNationalId(selfieWithNationalId)
                .build();
    }

    public IdentityDocument toIdentityDocument(
            User userRef, HostCredentials credentials, String nationalIdFrontUrl,
            String selfieWithNationalIdUrl) {

        int TWO_YEARS_IN_SECONDS = 60 * 60 * 24 * 365 * 2;
        return IdentityDocument.builder()
                .owner(userRef)
                .fullName(credentials.fullName())
                .expiryDate(Instant.now().plusSeconds(TWO_YEARS_IN_SECONDS))
                .issueDate(Instant.now())
                .nationalIdNumber(credentials.nationalIdNumber())
                .idFrontImageUrl(nationalIdFrontUrl)
                .selfieWithIdUrl(selfieWithNationalIdUrl)
                .build();
    }

    public IdentityDocumentDto toIdentityDocumentDto(IdentityDocument doc) {
        Integer verifiedById = Optional.ofNullable(doc.getVerifiedBy())
                .map(User::getId)
                .orElse(null);
        return IdentityDocumentDto.builder()
                .id(doc.getId())
                .ownerId(doc.getOwner().getId())
                .nationalIdNumber(doc.getNationalIdNumber())
                .idFrontImageUrl(doc.getIdFrontImageUrl())
                .selfieWithIdUrl(doc.getSelfieWithIdUrl())
                .expiryDate(doc.getExpiryDate())
                .fullName(doc.getFullName())
                .issueDate(doc.getIssueDate())
                .status(doc.getStatus().name())
                .rejectionReason(doc.getRejectionReason())
                .verifiedAt(doc.getVerifiedAt())
                .verifiedById(verifiedById)
                .build();
    }
}
