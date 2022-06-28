package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Specify JobDetails in Trigger.
 */
class TriggerForJobTest {

    @Test
    void schedule() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .storeDurably()
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .forJob(jobDetail)
                .startNow()
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.addJob(jobDetail, true);
        scheduler.scheduleJob(trigger);
        await().timeout(1, MINUTES).until(() -> WaitJob.done);
        scheduler.shutdown(true);
    }

    public static class WaitJob implements Job {
        static boolean done = false;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                Thread.sleep(500);
                System.out.println("Job is done: " + context.getJobDetail().getKey());
                done = true;
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
