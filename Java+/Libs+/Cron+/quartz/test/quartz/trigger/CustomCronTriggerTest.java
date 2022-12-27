package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.MultiResultListener;
import quartz.NowResultJob;

import java.text.ParseException;
import java.time.Duration;

import static org.quartz.JobBuilder.newJob;

class CustomCronTriggerTest {
    @Test
    void trigger() throws SchedulerException, ParseException {
        var jobDetail = newJob(NowResultJob.class).build();
        var trigger = new DelayCronTriggerImpl(Duration.ofSeconds(3));
        trigger.setName("CustomCronTrigger1");
        trigger.setCronExpression("0/10 * * * * ?");
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        MultiResultListener.<String>assign(scheduler, jobDetail).waitForResults(3);
        scheduler.shutdown(true);
    }
}
