package actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthStatusApp {
    public static void main(String[] args) {
        SpringApplication.run(HealthStatusApp.class, args);
    }
}
