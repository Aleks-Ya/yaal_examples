package scheduling.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.Task;

@Configuration
@EnableScheduling
class TaskConfig {

    @Bean
    public Task task() {
        return new Task(() -> System.out.println("TASK"));
    }


    @Bean
    public Task task2() {
        return new IntervalTask(() -> System.out.println("TASK"), 1000);
    }

}