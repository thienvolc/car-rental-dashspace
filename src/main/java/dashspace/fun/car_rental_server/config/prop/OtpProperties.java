package dashspace.fun.car_rental_server.config.prop;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "otp")
public class OtpProperties {
    private int expirationSec;
    private int codeLength;

    private Duration expirationDuration;

    @PostConstruct
    public void initializeDerivedValues() {
        this.expirationDuration = Duration.ofSeconds(this.expirationSec);
    }
}
