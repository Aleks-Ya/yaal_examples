package quartz.trigger.simple;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Start a job with delay.
 * Doesn't work with a cron schedule.
 */
class StartWithDelayTest {
    @Test
    void plus() throws SchedulerException, InterruptedException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).build();
            var delaySec = 1;
            var startDate = Date.from(Instant.now().plusSeconds(delaySec));
            var trigger = newTrigger().startAt(startDate).build();
            scheduler.scheduleJob(jobDetail, trigger);
            var jobsListener = factory.getJobsListener();
            assertThat(jobsListener.getToBeExecutedJobs()).isEmpty();
            Thread.sleep((delaySec + 1) * 1000);
            assertThat(jobsListener.getToBeExecutedJobs()).hasSize(1);
        }
    }

    @Test
    void startDelayed() throws SchedulerException, InterruptedException {
        try (var factory = new Factory()) {
            var scheduler = factory.newSchedulerNotStart();
            var jobDetail = newJob(UniversalJob.class).build();
            var delaySec = 1;
            var trigger = newTrigger().startNow().build();
            scheduler.startDelayed(delaySec);
            scheduler.scheduleJob(jobDetail, trigger);
            var jobsListener = factory.getJobsListener();
            assertThat(jobsListener.getToBeExecutedJobs()).isEmpty();
            Thread.sleep((delaySec + 1) * 1000);
            assertThat(jobsListener.getToBeExecutedJobs()).hasSize(1);
        }
    }
}
