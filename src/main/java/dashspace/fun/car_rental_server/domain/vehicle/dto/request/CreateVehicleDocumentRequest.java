package dashspace.fun.car_rental_server.domain.vehicle.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreateVehicleDocumentRequest(
        Integer vehicleId,
        MultipartFile registration,
        MultipartFile inspection,
        MultipartFile insurance,
        MultipartFile frontImage,
        MultipartFile leftImage,
        MultipartFile rightImage,
        MultipartFile backImage
) {
}
