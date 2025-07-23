package dashspace.fun.car_rental_server.host_document.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record IdentityDocumentDto(
        Integer id,

        @JsonProperty("owner_id")
        Integer ownerId,

        @JsonProperty("verified_by_user_id")
        Integer verifiedById,

        @JsonProperty("verified_at")
        Instant verifiedAt,

        @JsonProperty("national_id_number")
        String nationalIdNumber,

        @JsonProperty("full_name")
        String fullName,

        @JsonProperty("issue_date")
        Instant issueDate,

        @JsonProperty("expiry_date")
        Instant expiryDate,

        String status,
        @JsonProperty("rejection_reason")
        String rejectionReason,

        @JsonProperty("id_front_image_url")
        String idFrontImageUrl,

        @JsonProperty("selfie_with_id_url")
        String selfieWithIdUrl
) {}
