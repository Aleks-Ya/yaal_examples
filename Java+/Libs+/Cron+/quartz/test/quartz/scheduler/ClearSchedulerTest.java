package quartz.scheduler;

import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Clear a {@link Scheduler}.
 */
class ClearSchedulerTest {

    @Test
    void clear() throws SchedulerException {
        var jobDetail1 = newJob(EmptyJob.class).build();
        var jobDetail2 = newJob(EmptyJob.class).build();
        var trigger1 = newTrigger().startAt(Date.from(Instant.now().plusSeconds(5000))).build();
        var trigger2 = newTrigger().startAt(Date.from(Instant.now().plusSeconds(3000))).build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();
        assertThat(scheduler.checkExists(jobDetail1.getKey())).isFalse();
        assertThat(scheduler.checkExists(jobDetail2.getKey())).isFalse();
        scheduler.scheduleJob(jobDetail1, trigger1);
        scheduler.scheduleJob(jobDetail2, trigger2);
        assertThat(scheduler.checkExists(jobDetail1.getKey())).isTrue();
        assertThat(scheduler.checkExists(jobDetail2.getKey())).isTrue();
        assertThat(scheduler.getTriggersOfJob(jobDetail1.getKey())).asList().containsExactly(trigger1);
        assertThat(scheduler.getTriggersOfJob(jobDetail2.getKey())).asList().containsExactly(trigger2);

        scheduler.clear();
        assertThat(scheduler.checkExists(jobDetail1.getKey())).isFalse();
        assertThat(scheduler.checkExists(jobDetail2.getKey())).isFalse();
        assertThat(scheduler.getTriggersOfJob(jobDetail1.getKey())).asList().isEmpty();

        scheduler.shutdown(true);
    }

}
