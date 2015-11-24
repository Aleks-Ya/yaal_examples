package scheduling;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class TaskSchedulerConfig {

    {
        TaskScheduler scheduler = new ConcurrentTaskScheduler();
        scheduler.scheduleWithFixedDelay(() -> System.out.println("do TaskScheduler"), 1000);
    }

}