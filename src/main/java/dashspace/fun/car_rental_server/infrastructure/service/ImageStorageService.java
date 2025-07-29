package dashspace.fun.car_rental_server.infrastructure.service;

import dashspace.fun.car_rental_server.app.aop.BusinessException;
import dashspace.fun.car_rental_server.domain.common.constant.ResponseCode;
import dashspace.fun.car_rental_server.infrastructure.adapter.CloudinaryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageStorageService {

    private final CloudinaryAdapter cloudinaryAdapter;

    @SuppressWarnings("rawtypes")
    public String upload(MultipartFile image) {
        try {
            Map response = cloudinaryAdapter.uploadImage(image);
            return (String) response.get("secure_url");
        } catch (IOException _) {
            throw new BusinessException(ResponseCode.IMAGE_UPLOAD_FAILED);
        }
    }

    public void destroy(String imageUrl) {
        try {
            cloudinaryAdapter.destroyImage(imageUrl);
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
