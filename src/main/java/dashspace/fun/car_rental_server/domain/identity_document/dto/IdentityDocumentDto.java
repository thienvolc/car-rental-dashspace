package dashspace.fun.car_rental_server.domain.identity_document.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record IdentityDocumentDto(
        Integer id,
        Integer userId,
        Integer verifiedById,
        Instant verifiedAt,
        String nationalIdNumber,
        String fullName,
        LocalDate issueDate,
        LocalDate expiryDate,
        String status,
        String rejectionReason,
        String idFrontImageUrl,
        String selfieWithIdUrl
) {
}
