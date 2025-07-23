package dashspace.fun.car_rental_server.vehicle_document.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UpdateVehicleDocumentRequest(
        @NotNull Integer vehicleId,
        @Nullable MultipartFile registration,
        @Nullable MultipartFile inspection,
        @Nullable MultipartFile insurance,
        @Nullable MultipartFile frontImage,
        @Nullable MultipartFile leftImage,
        @Nullable MultipartFile rightImage,
        @Nullable MultipartFile backImage
) {
}
