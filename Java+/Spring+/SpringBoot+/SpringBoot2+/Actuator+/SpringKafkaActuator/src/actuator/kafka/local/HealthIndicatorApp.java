package actuator.kafka.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.RandomUtil;

import static util.ResourceUtil.resourceToPath;

/**
 * Using {@link org.springframework.boot.actuate.health.ApplicationHealthIndicator}.<br/>
 * Endpoint: http://localhost:8085/actuator/health
 */
@SpringBootApplication
class HealthIndicatorApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location", resourceToPath(HealthIndicatorApp.class, "application.yaml"));
        var groupId = "my_group1";
        System.setProperty("kafka.bootstrapAddress", "localhost:9092");
        System.setProperty("topic", "topic-" + RandomUtil.randomIntPositive());
        System.setProperty("group", groupId);

        SpringApplication.run(HealthIndicatorApp.class, args);
    }
}
