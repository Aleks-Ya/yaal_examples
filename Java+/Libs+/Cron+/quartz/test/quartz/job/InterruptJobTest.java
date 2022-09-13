package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.StdSchedulerFactory;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Specify JobDetails in Trigger.
 */
class InterruptJobTest {

    @Test
    void interruptJob() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        scheduler.scheduleJob(jobDetail, trigger);
        assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
        await().timeout(30, SECONDS).until(() -> WaitJob.started);
        var result = scheduler.interrupt(jobDetail.getKey());
        assertThat(result).isTrue();
        await().timeout(30, SECONDS).until(() -> WaitJob.done);
        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        scheduler.shutdown(true);
    }

    public static class WaitJob implements InterruptableJob {
        static boolean started = false;
        static boolean done = false;
        private boolean keepWorking = true;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                started = true;
                while (keepWorking) {
                    Thread.sleep(500);
                }
                done = true;
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }

        @Override
        public void interrupt() throws UnableToInterruptJobException {
            keepWorking = false;
        }
    }
}
