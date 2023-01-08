package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.WAIT_MILLIS;

/**
 * Unschedule a Job.
 */
class UnscheduleJobTest {

    @Test
    void unscheduleJobSingleTrigger() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .usingJobData(WAIT_MILLIS, 500)
                    .build();
            var trigger = newTrigger().startAt(Date.from(Instant.now().plusSeconds(5000))).build();
            assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
            scheduler.scheduleJob(jobDetail, trigger);
            assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
            boolean unscheduled = scheduler.unscheduleJob(trigger.getKey());
            assertThat(unscheduled).isTrue();
            assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        }
    }

    @Test
    void unscheduleJobMultipleTriggers() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).usingJobData(WAIT_MILLIS, 500).build();
            var trigger1 = newTrigger().startAt(Date.from(Instant.now().plusSeconds(5000))).build();
            var trigger2 = newTrigger().startAt(Date.from(Instant.now().plusSeconds(3000))).build();
            assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
            scheduler.scheduleJob(jobDetail, Set.of(trigger1, trigger2), true);
            assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
            assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).asList().containsExactlyInAnyOrder(trigger1, trigger2);
            boolean unscheduled = scheduler.unscheduleJob(trigger1.getKey());
            assertThat(unscheduled).isTrue();
            assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).asList().containsExactly(trigger2);
            assertThat(scheduler.checkExists(jobDetail.getKey())).isTrue();
        }
    }
}
