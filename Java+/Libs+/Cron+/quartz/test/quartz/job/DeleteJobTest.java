package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.Date;
import java.time.Instant;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Delete a Job.
 */
class DeleteJobTest {

    @Test
    void deleteNotStartedJob() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(Date.from(Instant.now().plusMillis(1000)))
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        assertThat(PauseJobTest.WaitJob.started).isFalse();
        assertThat(scheduler.getCurrentlyExecutingJobs()).isEmpty();
        assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
        var deleted = scheduler.deleteJob(jobDetail.getKey());
        assertThat(deleted).isTrue();
        assertThat(scheduler.getCurrentlyExecutingJobs()).isEmpty();
        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        scheduler.shutdown(true);
    }

    @Test
    void deleteStartedJob() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        await().timeout(5, SECONDS).until(() -> WaitJob.started);
        assertThat(scheduler.getCurrentlyExecutingJobs().stream()
                .map(JobExecutionContext::getJobDetail).toList()).contains(jobDetail);
        assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();

        var deleted = scheduler.deleteJob(jobDetail.getKey());
        assertThat(deleted).isTrue();
        assertThat(scheduler.getCurrentlyExecutingJobs().stream()
                .map(JobExecutionContext::getJobDetail).toList()).contains(jobDetail);
        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        await().timeout(30, SECONDS).until(() -> WaitJob.done);

        scheduler.shutdown(true);
    }


    public static class WaitJob implements Job {
        static boolean started = false;
        static boolean done = false;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                started = true;
                Thread.sleep(3000);
                System.out.println("Job is done: " + context.getJobDetail().getKey());
                done = true;
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
