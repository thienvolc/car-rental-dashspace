package dashspace.fun.car_rental_server.user.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreateDrivingLicenseRequest(
        DrivingLicenseCredentials credentials,
        MultipartFile backImage,
        MultipartFile frontImage
) {}
