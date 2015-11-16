package scheduling;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class ScheduledConfig {

    @Scheduled(fixedDelay = 1000)
    public void fixedDelay() {
        System.out.println(LocalDateTime.now());
    }

    @Scheduled(fixedDelayString = "${my-prop}")
    public void fixedDelayString() {
        System.out.println("String: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 1000)
    public void fixedRate() throws InterruptedException {
        System.out.println(LocalDateTime.now());
        Thread.sleep(500);
    }
}