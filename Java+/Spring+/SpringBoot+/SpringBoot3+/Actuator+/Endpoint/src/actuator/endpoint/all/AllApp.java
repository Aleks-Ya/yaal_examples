package actuator.endpoint.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.ResourceUtil;

@SpringBootApplication
class AllApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location", ResourceUtil.resourceToStrPath(AllApp.class, "application.yaml"));
        SpringApplication.run(AllApp.class, args);
    }
}
