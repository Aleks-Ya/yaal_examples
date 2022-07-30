package actuator.endpoint.loggers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.ResourceUtil;

import static util.ResourceUtil.resourceToPath;

@SpringBootApplication
class LoggersApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location", resourceToPath(LoggersApp.class, "application.yaml"));
        SpringApplication.run(LoggersApp.class, args);
    }
}
