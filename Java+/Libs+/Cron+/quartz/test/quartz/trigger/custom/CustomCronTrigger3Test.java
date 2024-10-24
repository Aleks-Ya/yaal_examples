package quartz.trigger.custom;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.text.ParseException;
import java.time.Duration;

import static org.quartz.JobBuilder.newJob;

class CustomCronTrigger3Test {
    @Test
    void trigger() throws SchedulerException, ParseException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).withIdentity("jobDetail3").build();
            var trigger = new DelayCronTriggerImpl3(Duration.ofSeconds(3));
            trigger.setName("CustomCronTrigger3");
            trigger.setCronExpression("0/10 * * * * ?");
            scheduler.scheduleJob(jobDetail, trigger);
            factory.assertJobExecutedWithoutExceptions(jobDetail, 5);
        }
    }
}
