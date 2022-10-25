package scheduling.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
class TaskSchedulerConfig {
    static {
        TaskScheduler scheduler = new ConcurrentTaskScheduler();
        scheduler.scheduleWithFixedDelay(() -> System.out.println("do TaskScheduler"), 1000);
    }
}