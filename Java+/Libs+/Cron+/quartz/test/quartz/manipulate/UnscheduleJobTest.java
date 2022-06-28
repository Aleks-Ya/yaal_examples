package quartz.manipulate;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.Date;
import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Unschedule a Job.
 */
class UnscheduleJobTest {

    @Test
    void unscheduleJobSingleTrigger() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class).build();
        var trigger = newTrigger().startAt(Date.from(Instant.now().plusSeconds(5000))).build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        scheduler.scheduleJob(jobDetail, trigger);
        assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
        scheduler.unscheduleJob(trigger.getKey());
        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        scheduler.shutdown(true);
    }

    @Test
    void unscheduleJobMultipleTriggers() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class).build();
        var trigger1 = newTrigger().startAt(Date.from(Instant.now().plusSeconds(5000))).build();
        var trigger2 = newTrigger().startAt(Date.from(Instant.now().plusSeconds(3000))).build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        scheduler.scheduleJob(jobDetail, Set.of(trigger1, trigger2), true);
        assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
        assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).asList().containsExactlyInAnyOrder(trigger1, trigger2);
        scheduler.unscheduleJob(trigger1.getKey());
        assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).asList().containsExactly(trigger2);
        assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
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
