package quartz.config.custom.both;

import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Configure Quartz in application.yaml
 */
@SpringBootTest(webEnvironment = NONE, classes = QuartzPropertiesVsApplicationYamlTest.QuartzConfig.class,
        properties = {"spring.config.location=classpath:config/custom/both/application.yaml",
                "org.quartz.properties=absent.properties"})
class QuartzPropertiesVsApplicationYamlTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    void test() throws SchedulerException {
        var name = scheduler.getSchedulerName();
        assertThat(name).isEqualTo("scheduler-quartz-properties-both");
    }

    @Configuration
    static class QuartzConfig {
        @Bean
        SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
            var jobFactory = new SpringBeanJobFactory();
            jobFactory.setApplicationContext(applicationContext);
            return jobFactory;
        }

        @Bean
        SchedulerFactoryBean scheduler(SpringBeanJobFactory springBeanJobFactory) {
            var schedulerFactory = new SchedulerFactoryBean();
            schedulerFactory.setConfigLocation(new ClassPathResource("config/custom/both/quartz.properties"));
            schedulerFactory.setJobFactory(springBeanJobFactory);
            return schedulerFactory;
        }
    }
}