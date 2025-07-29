package dashspace.fun.car_rental_server.domain.identity_document.dto.request;

import java.time.LocalDate;

public record HostRegistrationRequest(
        String email,
        String fullName,
        String nationalIdNumber,
        LocalDate issueDate,
        LocalDate expiryDate,
        String otpCode
) {
}
