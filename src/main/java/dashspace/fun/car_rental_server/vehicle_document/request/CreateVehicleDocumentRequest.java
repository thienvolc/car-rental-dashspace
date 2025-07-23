package dashspace.fun.car_rental_server.vehicle_document.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreateVehicleDocumentRequest(
        @NotNull Integer vehicleId,
        MultipartFile registration,
        MultipartFile inspection,
        MultipartFile insurance,
        MultipartFile frontImage,
        MultipartFile leftImage,
        MultipartFile rightImage,
        MultipartFile backImage
) {
}
