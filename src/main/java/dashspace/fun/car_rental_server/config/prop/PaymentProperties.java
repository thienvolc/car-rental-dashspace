package dashspace.fun.car_rental_server.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {

    private Map<String, PaymentProviderProperties> providers;

    @Getter
    @Setter
    public static class PaymentProviderProperties {
        private String terminalCode;
        private String hashSecret;
        private String payUrl;
        private String returnUrl;
        private String username;
    }
}
