package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Get the next fire time from a Trigger.
 */
class NextFireTimeTest {

    @Test
    void nextFireTime() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class).build();
        var startDate = Date.from(Instant.now().plusSeconds(1000));
        var trigger = newTrigger().startAt(startDate).build();

        assertThat(trigger.getNextFireTime()).isNull();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        assertThat(trigger.getNextFireTime()).isNull();
        scheduler.start();
        assertThat(trigger.getNextFireTime()).isNull();
        scheduler.scheduleJob(jobDetail, trigger);
        assertThat(trigger.getNextFireTime()).isEqualTo(startDate);
        scheduler.shutdown(true);
    }

    public static class EmptyJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
        }
    }
}
