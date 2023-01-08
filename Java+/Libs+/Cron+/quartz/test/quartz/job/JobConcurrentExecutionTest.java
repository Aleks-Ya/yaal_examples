package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.WAIT_MILLIS;

/**
 * The next fire happens before the previous fire is finished.
 */
class JobConcurrentExecutionTest {
    @Test
    void test() throws SchedulerException, InterruptedException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1", "group1")
                    .usingJobData(WAIT_MILLIS, 2000)
                    .build();
            var trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(simpleSchedule().repeatForever().withIntervalInMilliseconds(500))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            Thread.sleep(5000);
        }
    }
}
