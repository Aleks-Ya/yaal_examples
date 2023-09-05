package actuator.endpoint.health.diskspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import util.ResourceUtil;

import java.io.File;

/**
 * Using {@link DiskSpaceHealthIndicator}.
 * Endpoints:
 * <ul>
 *     <li>http://localhost:8080/actuator/health</li>
 *     <li>http://localhost:8080/actuator/health/diskSpace</li>
 * </ul>
 */
@SpringBootApplication
class DiskSpaceHealthIndicatorApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location",
                ResourceUtil.resourceToStrPath(DiskSpaceHealthIndicatorApp.class, "application.yaml"));
        SpringApplication.run(DiskSpaceHealthIndicatorApp.class, args);
    }

    @Bean
    DiskSpaceHealthIndicator diskSpaceHealthIndicator() {
        var userHomeDir = new File(System.getProperty("user.home"));
        return new DiskSpaceHealthIndicator(userHomeDir, DataSize.ofGigabytes(5));
    }
}
