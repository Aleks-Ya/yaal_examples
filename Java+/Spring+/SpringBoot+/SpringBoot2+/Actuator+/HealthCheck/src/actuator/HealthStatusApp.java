package actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class HealthStatusApp {
    public static void main(String[] args) {
        SpringApplication.run(HealthStatusApp.class, args);
    }
}
