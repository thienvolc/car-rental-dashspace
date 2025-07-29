package dashspace.fun.car_rental_server.domain.driving_license.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
public record CreateDrivingLicenseRequest(
        Integer userId,
        String licenseNumber,
        String fullName,
        LocalDate issueDate,
        LocalDate expiryDate,
        MultipartFile backImage,
        MultipartFile frontImage
) {}
