package actuator.endpoint.loggers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.ResourceUtil;

@SpringBootApplication
class LoggersApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location", ResourceUtil.resourceToStrPath(LoggersApp.class, "application.yaml"));
        SpringApplication.run(LoggersApp.class, args);
    }
}
