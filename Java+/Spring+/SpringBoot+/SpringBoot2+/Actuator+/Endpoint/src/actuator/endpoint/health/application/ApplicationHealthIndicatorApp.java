package actuator.endpoint.health.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.ResourceUtil;

/**
 * Using {@link org.springframework.boot.actuate.health.ApplicationHealthIndicator}.<br/>
 * Endpoint: http://localhost:8080/actuator/health
 */
@SpringBootApplication
class ApplicationHealthIndicatorApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location",
                ResourceUtil.resourceToStrPath(ApplicationHealthIndicatorApp.class, "application.yaml"));
        SpringApplication.run(ApplicationHealthIndicatorApp.class, args);
    }
}
