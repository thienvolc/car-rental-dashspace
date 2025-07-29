package dashspace.fun.car_rental_server.domain.identity_document.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record IdentityDocumentImages(
    MultipartFile nationalIdFront,
    MultipartFile selfieWithNationalId
) {
}
