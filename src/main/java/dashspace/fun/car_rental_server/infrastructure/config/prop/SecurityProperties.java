package dashspace.fun.car_rental_server.infrastructure.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> disposableEmail;
}
