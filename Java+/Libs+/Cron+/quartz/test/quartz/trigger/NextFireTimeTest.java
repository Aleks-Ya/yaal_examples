package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import quartz.Factory;
import quartz.UniversalJob;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Get the next fire time from a Trigger.
 */
class NextFireTimeTest {
    private static void printSeveralNextFireTimes(Trigger trigger) throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            var jobDetail = newJob(UniversalJob.class).build();
            scheduler.scheduleJob(jobDetail, trigger);
            var startTime = new Date();
            System.out.println("  Now:        " + formatter.format(startTime));
            System.out.println("  Start time: " + formatter.format(trigger.getStartTime()));

            var fireNumber = 10;
            for (int i = 0; i < fireNumber; i++) {
                var fireTime = trigger.getFireTimeAfter(startTime);
                System.out.printf("%2d Fire time: %s\n", i + 1, formatter.format(fireTime));
                startTime = fireTime;
            }
        }
    }

    @Test
    void nextFireTime() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).build();
            var startDate = Date.from(Instant.now().plusSeconds(1000));
            var trigger = newTrigger().startAt(startDate).build();

            assertThat(trigger.getNextFireTime()).isNull();
            assertThat(trigger.getNextFireTime()).isNull();
            assertThat(trigger.getNextFireTime()).isNull();
            scheduler.scheduleJob(jobDetail, trigger);
            assertThat(trigger.getNextFireTime()).isEqualTo(startDate);
        }
    }

    @Test
    void severalNextFireTimes_simpleSchedule() throws SchedulerException {
        var startTime = Date.from(Instant.now().plusSeconds(1000));
        var trigger = newTrigger().withSchedule(simpleSchedule().withIntervalInHours(1).repeatForever())
                .startAt(startTime).build();
        printSeveralNextFireTimes(trigger);
    }

    @Test
    void severalNextFireTimes_cronSchedule() throws SchedulerException {
        var startTime = Date.from(Instant.now().plus(10, ChronoUnit.MINUTES));
        var trigger = newTrigger().withSchedule(cronSchedule("0 */10 * * * ? *")).startAt(startTime).build();
        printSeveralNextFireTimes(trigger);
    }
}
