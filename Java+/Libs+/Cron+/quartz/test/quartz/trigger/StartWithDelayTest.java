package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;
import quartz.SingleResultListener;

import java.sql.Date;
import java.time.Instant;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Start a job with delay.
 * Doesn't work with a cron schedule.
 */
class StartWithDelayTest {
    @Test
    void cron() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class).build();
        var delaySec = 1;
        var startDate = Date.from(Instant.now().plusSeconds(delaySec));
        var trigger = newTrigger().startAt(startDate).build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        var listener = SingleResultListener.<String>assign(scheduler, jobDetail);
        assertThat(listener.isToBeExecuted()).isFalse();
        await().pollDelay(delaySec + 1, SECONDS).until(() -> true);
        assertThat(listener.isToBeExecuted()).isTrue();
        listener.waitForFinish();
        scheduler.shutdown(true);
    }
}
