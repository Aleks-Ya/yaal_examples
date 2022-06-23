package quartz;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;

/**
 * Trigger a Job without a Trigger.
 */
class RunJobWithoutTriggerTest {

    @Test
    void trigger() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .storeDurably()
                .build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.addJob(jobDetail, true);
        scheduler.triggerJob(jobDetail.getKey());
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
