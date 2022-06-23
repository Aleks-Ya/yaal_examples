package quartz;

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
 * Pause and resume a Job.
 */
class PauseJobTest {

    @Test
    void unscheduleJob() throws SchedulerException, InterruptedException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .build();
        var delay = 500;
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(Date.from(Instant.now().plusMillis(delay)))
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        assertThat(WaitJob.started).isFalse();
        scheduler.pauseJob(jobDetail.getKey());
        Thread.sleep(delay * 2);
        assertThat(WaitJob.started).isFalse();
        scheduler.resumeJob(jobDetail.getKey());
        await().timeout(5, SECONDS).until(() -> WaitJob.done);
        scheduler.shutdown(true);
    }

    public static class WaitJob implements Job {
        static boolean started = false;
        static boolean done = false;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                started = true;
                Thread.sleep(500);
                System.out.println("Job is done: " + context.getJobDetail().getKey());
                done = true;
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
