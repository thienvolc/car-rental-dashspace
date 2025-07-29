package dashspace.fun.car_rental_server.infrastructure.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dashspace.fun.car_rental_server.infrastructure.config.prop.CloudinaryProperties;
import dashspace.fun.car_rental_server.infrastructure.constant.CloudinaryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    private final CloudinaryProperties cloudinaryProperties;

    @Bean
    public Cloudinary cloudinaryClient() {
        return new Cloudinary(getConfiguration());
    }

    @SuppressWarnings("rawtypes")
    private Map getConfiguration() {
        return ObjectUtils.asMap(
                CloudinaryParams.CLOUD_NAME, this.cloudinaryProperties.getCloudName(),
                CloudinaryParams.API_KEY, this.cloudinaryProperties.getApiKey(),
                CloudinaryParams.API_SECRET, this.cloudinaryProperties.getApiSecret(),
                CloudinaryParams.SECURE, true);
    }
}
