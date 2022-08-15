package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class TriggerToStringTest {
    @Test
    void triggerToString() throws SchedulerException {
        var startDate = Date.from(Instant.parse("2050-12-03T10:15:30.00Z"));
        var trigger = newTrigger()
                .withSchedule(cronSchedule("0/2 * * * * ?"))
                .startAt(startDate)
                .withIdentity("triggerName1", "triggerGroup1")
                .build();
        assertThat(trigger).hasToString(
                "Trigger 'triggerGroup1.triggerName1':  triggerClass: 'org.quartz.impl.triggers.CronTriggerImpl " +
                        "calendar: 'null' misfireInstruction: 0 nextFireTime: null");

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        var jobDetail = newJob(EmptyJob.class).build();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        assertThat(trigger).hasToString(
                "Trigger 'triggerGroup1.triggerName1':  triggerClass: 'org.quartz.impl.triggers.CronTriggerImpl " +
                        "calendar: 'null' misfireInstruction: 0 nextFireTime: Sat Dec 03 13:15:30 MSK 2050");

        scheduler.shutdown(true);
    }
}
