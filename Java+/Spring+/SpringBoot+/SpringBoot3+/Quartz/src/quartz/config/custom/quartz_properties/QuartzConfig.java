package quartz.config.custom.quartz_properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;


/**
 * Define JobDetails and Trigger in Spring config.
 */
@Configuration
class QuartzConfig {
    @Bean
    SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        var jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    SchedulerFactoryBean scheduler(SpringBeanJobFactory springBeanJobFactory) {
        var schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("config/custom/quartz_properties/quartz.properties"));
        schedulerFactory.setJobFactory(springBeanJobFactory);
        return schedulerFactory;
    }
}
