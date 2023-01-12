package quartz.trigger.simple;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

class SimpleTriggerTest {
    @Test
    void never_work() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var startDate = Date.from(Instant.parse("2007-12-03T10:15:30.00Z").minusSeconds(300));
            var jobDetail = newJob(UniversalJob.class).build();
            var trigger = newTrigger()
                    .withSchedule(simpleSchedule().withMisfireHandlingInstructionNextWithExistingCount())
                    .startAt(startDate)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            assertThat(trigger.getNextFireTime()).isEqualTo(startDate);
            await().until(() -> !factory.getTriggersListener().getMisfiredTriggers().isEmpty());
            await().during(ofSeconds(5)).until(() -> factory.getJobsListener().getTimesJobWasExecuted(jobDetail) == 0);
        }
    }

    @Test
    void never_notWork_1() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var startDate = Date.from(Instant.parse("2007-12-03T10:15:30.00Z").minusSeconds(300));
            var jobDetail = newJob(UniversalJob.class).build();
            var trigger = newTrigger().startAt(startDate).build();
            scheduler.scheduleJob(jobDetail, trigger);
            assertThat(trigger.getNextFireTime()).isEqualTo(startDate);
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
        }
    }

    @Test
    void never_notWork_2() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).build();
            var trigger = newTrigger().build();
            scheduler.scheduleJob(jobDetail, trigger);
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
        }
    }
}
