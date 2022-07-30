package actuator.endpoint.health.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static util.ResourceUtil.resourceToPath;

/**
 * Using {@link DiskSpaceHealthIndicator}.
 * Endpoints:
 * - http://localhost:8080/actuator/health
 * - http://localhost:8080/actuator/health/success
 */
@SpringBootApplication
class CustomHealthIndicatorApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location",
                resourceToPath(CustomHealthIndicatorApp.class, "CustomHealthIndicatorApp.yaml"));
        SpringApplication.run(CustomHealthIndicatorApp.class, args);
    }
}
