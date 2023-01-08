package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Specify JobDetails in Trigger.
 */
class TriggerForJobTest {
    @Test
    void schedule() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).storeDurably().build();
            var trigger = newTrigger().forJob(jobDetail).startNow().build();
            scheduler.addJob(jobDetail, true);
            scheduler.scheduleJob(trigger);
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
        }
    }
}
