package dashspace.fun.car_rental_server.infrastructure.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class HealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        try {
            String computerName = InetAddress.getLocalHost().getHostName();
            return Health.up()
                    .withDetail("computerName", computerName)
                    .build();
        } catch (Exception ex) {
            return Health.down()
                    .withDetail("Error", ex.getMessage())
                    .build();
        }
    }
}
