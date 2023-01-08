package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.WAIT_MILLIS;

/**
 * Delete a Job.
 */
class DeleteJobTest {

    @Test
    void deleteNotStartedJob() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1", "group1")
                    .usingJobData(WAIT_MILLIS, 3000)
                    .build();
            var trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(Date.from(Instant.now().plusSeconds(60)))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            var jobsListener = factory.getJobsListener();
            assertThat(jobsListener.getWasExecutedJobs()).isEmpty();
            assertThat(scheduler.getCurrentlyExecutingJobs()).isEmpty();
            assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
            var deleted = scheduler.deleteJob(jobDetail.getKey());
            assertThat(deleted).isTrue();
            assertThat(scheduler.getCurrentlyExecutingJobs()).isEmpty();
            assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
            assertThat(jobsListener.getWasExecutedJobs()).isEmpty();
        }
    }

    @Test
    void deleteStartedJob() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1", "group1")
                    .usingJobData(WAIT_MILLIS, 3000)
                    .build();
            var trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            var jobsListener = factory.getJobsListener();
            await().timeout(5, SECONDS).until(() -> !jobsListener.getToBeExecutedJobs().isEmpty());
            assertThat(jobsListener.getWasExecutedJobs()).isEmpty();
            assertThat(scheduler.getCurrentlyExecutingJobs().stream()
                    .map(JobExecutionContext::getJobDetail).toList()).contains(jobDetail);
            assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();

            var deleted = scheduler.deleteJob(jobDetail.getKey());
            assertThat(deleted).isTrue();
            assertThat(scheduler.getCurrentlyExecutingJobs().stream()
                    .map(JobExecutionContext::getJobDetail).toList()).contains(jobDetail);
            assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
        }
    }
}
