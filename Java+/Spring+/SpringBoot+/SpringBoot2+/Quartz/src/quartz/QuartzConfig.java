package quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;


@Configuration
class QuartzConfig {
    static final int REPEAT_COUNT = 3;

    @Bean
    JobDetailFactoryBean jobDetail() {
        var jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SampleJob.class);
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    SimpleTriggerFactoryBean trigger(JobDetail job) {
        var trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setRepeatInterval(300);
        trigger.setRepeatCount(REPEAT_COUNT);
        return trigger;
    }

    @Bean
    SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        var jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job, SpringBeanJobFactory springBeanJobFactory) {
        var schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactory.setJobFactory(springBeanJobFactory);
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);
        return schedulerFactory;
    }
}
