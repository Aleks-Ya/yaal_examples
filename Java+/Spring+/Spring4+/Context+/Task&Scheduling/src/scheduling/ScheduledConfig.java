package scheduling;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class ScheduledConfig {
    public static final Integer DELAY_MILLISEC = 1000;// must be public
    public static final Integer DELAY_SEC = 10;// must be public

    @Scheduled(fixedDelay = 1000)
    public void fixedDelay() {
        System.out.println(LocalDateTime.now());
    }

    @Scheduled(fixedDelayString = "${my-prop}")
    public void fixedDelayString() {
        System.out.println("String: " + LocalDateTime.now());
    }

    @Scheduled(fixedDelayString = "${not-exists-prop:1000}")
    public void fixedDelayStringDefault() {
        System.out.println("String: " + LocalDateTime.now());
    }

    @Scheduled(fixedDelayString = "${not-exists-prop:#{T(scheduling.ScheduledConfig).DELAY_MILLISEC}}")
    public void fixedDelayStringDefaultConstant() {
        System.out.println("fixedDelayStringDefaultConstant: " + LocalDateTime.now());
    }

    @Scheduled(fixedDelayString = "#{ (systemProperties['not-exists-prop']?:T(scheduling.ScheduledConfig).DELAY_SEC) * 1000}")
    public void fixedDelayStringDefaultConstantMultiplied() {
        System.out.println("fixedDelayStringDefaultConstant: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 1000)
    public void fixedRate() throws InterruptedException {
        System.out.println(LocalDateTime.now());
        Thread.sleep(500);
    }
}