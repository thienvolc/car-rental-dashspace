package dashspace.fun.car_rental_server.common.util;

import dashspace.fun.car_rental_server.common.adapter.CloudinaryAdapter;
import dashspace.fun.car_rental_server.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static dashspace.fun.car_rental_server.exception.ErrorCode.IMAGE_UPLOAD_FAILED;

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
            throw new BusinessException(IMAGE_UPLOAD_FAILED);
        }
    }

    public void destroy(String imageUrl) {
        cloudinaryAdapter.destroyImage(imageUrl);
    }
}
