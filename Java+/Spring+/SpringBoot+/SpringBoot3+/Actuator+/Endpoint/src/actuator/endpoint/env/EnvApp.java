package actuator.endpoint.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static util.ResourceUtil.resourceToPath;

@SpringBootApplication
class EnvApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location", resourceToPath(EnvApp.class, "application.yaml"));
        SpringApplication.run(EnvApp.class, args);
    }
}
