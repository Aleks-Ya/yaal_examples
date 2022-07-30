package actuator.endpoint.health.custom;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("success")
class SuccessHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().withDetail("person", "John").build();
    }
}
