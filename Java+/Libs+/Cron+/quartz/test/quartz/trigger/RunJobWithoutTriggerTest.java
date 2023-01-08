package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import static org.quartz.JobBuilder.newJob;

/**
 * Trigger a Job without a Trigger.
 */
class RunJobWithoutTriggerTest {
    @Test
    void trigger() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).storeDurably().build();
            scheduler.addJob(jobDetail, true);
            scheduler.triggerJob(jobDetail.getKey());
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
        }
    }
}
