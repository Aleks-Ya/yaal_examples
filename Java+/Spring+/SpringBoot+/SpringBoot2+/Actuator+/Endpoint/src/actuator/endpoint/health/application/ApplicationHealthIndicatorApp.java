package actuator.endpoint.health.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static util.ResourceUtil.resourceToPath;

/**
 * Using {@link org.springframework.boot.actuate.health.ApplicationHealthIndicator}.
 * Endpoint: http://localhost:8080/actuator/health
 */
@SpringBootApplication
class ApplicationHealthIndicatorApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location",
                resourceToPath(ApplicationHealthIndicatorApp.class, "ApplicationHealthIndicatorApp.yaml"));
        SpringApplication.run(ApplicationHealthIndicatorApp.class, args);
    }
}
