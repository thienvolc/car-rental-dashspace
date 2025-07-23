package dashspace.fun.car_rental_server.common.adapter;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dashspace.fun.car_rental_server.common.util.CloudinaryUtils;
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
        return ObjectUtils.asMap("resource_type", "auto", "unique_filename", true);
    }

    public void destroyImage(String url) {
        try {
            tryDestroyImage(url);
        } catch (IOException | IllegalArgumentException _) {
        }
    }

    private void tryDestroyImage(String url) throws IOException,
            IllegalArgumentException {

        String publicId = CloudinaryUtils.tryExtractPublicIdFromImageUrl(url);
        cloudinary.uploader()
                .destroy(publicId, ObjectUtils.emptyMap());
    }
}
