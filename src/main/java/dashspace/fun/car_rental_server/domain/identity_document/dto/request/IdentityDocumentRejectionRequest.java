package dashspace.fun.car_rental_server.domain.identity_document.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IdentityDocumentRejectionRequest(

        @JsonProperty("rejection_reason")
        String rejectionReason
) {
}
