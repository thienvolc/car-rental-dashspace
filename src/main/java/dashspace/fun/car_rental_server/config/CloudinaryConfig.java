package dashspace.fun.car_rental_server.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dashspace.fun.car_rental_server.config.prop.CloudinaryProperties;
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
        return ObjectUtils.asMap("cloud_name", this.cloudinaryProperties.getCloudName(),
                "api_key", this.cloudinaryProperties.getApiKey(),
                "api_secret", this.cloudinaryProperties.getApiSecret(),
                "secure", true);
    }
}
