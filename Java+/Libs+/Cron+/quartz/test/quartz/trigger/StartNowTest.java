package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class StartNowTest {
    @Test
    void startNow() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).build();
            var trigger = newTrigger().startNow().build();
            scheduler.scheduleJob(jobDetail, trigger);
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
        }
    }
}
