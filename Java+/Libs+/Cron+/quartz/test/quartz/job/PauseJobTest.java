package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.WAIT_MILLIS;

/**
 * Pause and resume a Job.
 */
class PauseJobTest {

    @Test
    void unscheduleJob() throws SchedulerException, InterruptedException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1")
                    .usingJobData(WAIT_MILLIS, 500)
                    .build();
            var delay = 500;
            var trigger = newTrigger()
                    .withIdentity("trigger1")
                    .startAt(Date.from(Instant.now().plusMillis(delay)))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            var jobsListener = factory.getJobsListener();
            assertThat(jobsListener.getToBeExecutedJobs()).isEmpty();
            assertThat(jobsListener.getWasExecutedJobs()).isEmpty();
            scheduler.pauseJob(jobDetail.getKey());
            Thread.sleep(delay * 2);
            assertThat(jobsListener.getToBeExecutedJobs()).isEmpty();
            assertThat(jobsListener.getWasExecutedJobs()).isEmpty();
            scheduler.resumeJob(jobDetail.getKey());
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
        }
    }
}
