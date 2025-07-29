package dashspace.fun.car_rental_server.infrastructure.adapter;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dashspace.fun.car_rental_server.infrastructure.constant.CloudinaryOptions;
import dashspace.fun.car_rental_server.infrastructure.util.CloudinaryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class CloudinaryAdapter {

    private final Cloudinary cloudinary;

    public Map uploadImage(MultipartFile image) throws IOException {
        return cloudinary.uploader()
                .upload(image.getBytes(), createUploadOptions());
    }

    private Map createUploadOptions() {
        return ObjectUtils.asMap(
                CloudinaryOptions.RESOURCE_TYPE, "auto",
                CloudinaryOptions.UNIQUE_FILENAME, true
        );
    }

    public void destroyImage(String url) throws IOException, IllegalArgumentException {
        String publicId = CloudinaryUtil.extractPublicIdFromImageUrl(url);
        cloudinary.uploader()
                .destroy(publicId, ObjectUtils.emptyMap());
    }
}
