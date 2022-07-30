package actuator.endpoint.health.diskspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import java.io.File;

import static util.ResourceUtil.resourceToPath;

/**
 * Using {@link DiskSpaceHealthIndicator}.
 * Endpoints:
 * - http://localhost:8080/actuator/health
 * - http://localhost:8080/actuator/health/diskSpace
 */
@SpringBootApplication
class DiskSpaceHealthIndicatorApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location",
                resourceToPath(DiskSpaceHealthIndicatorApp.class, "DiskSpaceHealthIndicatorApp.yaml"));
        SpringApplication.run(DiskSpaceHealthIndicatorApp.class, args);
    }

    @Bean
    DiskSpaceHealthIndicator diskSpaceHealthIndicator() {
        var userHomeDir = new File(System.getProperty("user.home"));
        return new DiskSpaceHealthIndicator(userHomeDir, DataSize.ofGigabytes(5));
    }
}
