package actuator.endpoint.health.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.ResourceUtil;

/**
 * Using {@link DiskSpaceHealthIndicator}.
 * Endpoints:
 * <ul>
 *     <li>http://localhost:8080/actuator/health</li>
 *     <li>http://localhost:8080/actuator/health/success</li>
 * </ul>
 */
@SpringBootApplication
class CustomHealthIndicatorApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location",
                ResourceUtil.resourceToStrPath(CustomHealthIndicatorApp.class, "application.yaml"));
        SpringApplication.run(CustomHealthIndicatorApp.class, args);
    }
}
