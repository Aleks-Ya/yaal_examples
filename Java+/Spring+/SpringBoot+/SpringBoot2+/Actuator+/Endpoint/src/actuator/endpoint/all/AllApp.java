package actuator.endpoint.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static util.ResourceUtil.resourceToPath;

@SpringBootApplication
class AllApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location", resourceToPath(AllApp.class, "application.yaml"));
        SpringApplication.run(AllApp.class, args);
    }
}
