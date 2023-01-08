package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class TriggerToStringTest {
    @Test
    void triggerToString() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var startDate = Date.from(Instant.parse("2050-12-03T10:15:30.00Z"));
            var trigger = newTrigger()
                    .withSchedule(cronSchedule("0/2 * * * * ?"))
                    .startAt(startDate)
                    .withIdentity("triggerName1", "triggerGroup1")
                    .build();
            assertThat(trigger).hasToString(
                    "Trigger 'triggerGroup1.triggerName1':  triggerClass: 'org.quartz.impl.triggers.CronTriggerImpl " +
                            "calendar: 'null' misfireInstruction: 0 nextFireTime: null");

            var jobDetail = newJob(UniversalJob.class).build();
            scheduler.scheduleJob(jobDetail, trigger);
            assertThat(trigger).hasToString(
                    "Trigger 'triggerGroup1.triggerName1':  triggerClass: 'org.quartz.impl.triggers.CronTriggerImpl " +
                            "calendar: 'null' misfireInstruction: 0 nextFireTime: Sat Dec 03 18:15:30 PST 2050");
        }
    }
}
