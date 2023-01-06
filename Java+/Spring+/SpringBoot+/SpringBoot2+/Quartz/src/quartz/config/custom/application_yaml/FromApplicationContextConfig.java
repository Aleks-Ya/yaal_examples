package quartz.config.custom.application_yaml;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.util.Properties;


/**
 * Define JobDetails and Trigger in Spring config.
 */
@Configuration
@EnableAutoConfiguration
class FromApplicationContextConfig {
    @Bean
    @ConfigurationProperties("spring.quartz.properties")
    Properties quartzProps() {
        return new Properties();
    }

    @Bean
    SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        var jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    SchedulerFactoryBean scheduler() {
        var schedulerFactory = new SchedulerFactoryBean();
        var props = quartzProps();
        schedulerFactory.setQuartzProperties(props);
        return schedulerFactory;
    }
}
