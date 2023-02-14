package quartz.manual_job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
class QuartzConfig {
    @Bean
    SchedulerFactoryBean scheduler() {
        return new SchedulerFactoryBean();
    }
}
