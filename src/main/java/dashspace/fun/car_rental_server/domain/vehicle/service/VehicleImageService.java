package dashspace.fun.car_rental_server.domain.vehicle.service;

import dashspace.fun.car_rental_server.domain.vehicle.entity.Vehicle;
import dashspace.fun.car_rental_server.domain.vehicle.entity.VehicleImage;
import dashspace.fun.car_rental_server.infrastructure.service.ImageStorageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleImageService {

    private final ImageStorageService imageStorageService;

    public List<VehicleImage> createVehicleImages(Vehicle vehicleRef, @NonNull MultipartFile[] images) {
        if (ObjectUtils.isEmpty(images)) {
            return List.of();
        }

        int order = 0;
        var vehicleImages = new ArrayList<VehicleImage>();

        for (var img : images) {
            var vehicleImage = VehicleImage.builder()
                    .vehicle(vehicleRef)
                    .imageUrl(imageStorageService.upload(img))
                    .sortOrder(order++)
                    .build();
            vehicleImages.add(vehicleImage);
        }

        return vehicleImages;
    }
}
