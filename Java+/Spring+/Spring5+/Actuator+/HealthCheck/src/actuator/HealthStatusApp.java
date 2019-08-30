package actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Health status is available on http://localhost:8080/actuator/health
 */
@SpringBootApplication
class HealthStatusApp {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(HealthStatusApp.class, args);
    }
}
