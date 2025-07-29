package dashspace.fun.car_rental_server.domain.identity_document.mapper;

import dashspace.fun.car_rental_server.domain.identity_document.dto.IdentityDocumentDto;
import dashspace.fun.car_rental_server.domain.identity_document.entity.IdentityDocument;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IdentityDocumentMapper {

    public IdentityDocumentDto toDto(IdentityDocument identityDocument) {
        var verifiedBy = identityDocument.getVerifiedBy();
        Integer verifiedById = Objects.isNull(verifiedBy) ? null : verifiedBy.getId();

        return IdentityDocumentDto.builder()
                .id(identityDocument.getId())
                .userId(identityDocument.getOwner().getId())
                .nationalIdNumber(identityDocument.getNationalIdNumber())
                .idFrontImageUrl(identityDocument.getIdFrontImageUrl())
                .selfieWithIdUrl(identityDocument.getSelfieWithIdUrl())
                .expiryDate(identityDocument.getExpiryDate())
                .fullName(identityDocument.getFullName())
                .issueDate(identityDocument.getIssueDate())
                .status(identityDocument.getStatus().name())
                .rejectionReason(identityDocument.getRejectionReason())
                .verifiedAt(identityDocument.getVerifiedAt())
                .verifiedById(verifiedById)
                .build();
    }
}
