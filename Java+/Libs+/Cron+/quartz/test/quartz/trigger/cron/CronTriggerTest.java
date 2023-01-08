package quartz.trigger.cron;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import quartz.Factory;
import quartz.UniversalJob;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.Duration.ofMinutes;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class CronTriggerTest {
    @Test
    void cronSingleExecution() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).build();
            var trigger = newTrigger().withSchedule(cronSchedule("0/2 * * * * ?")).build();
            scheduler.scheduleJob(jobDetail, trigger);
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
        }
    }

    @Test
    void cronMultipleExecutions() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).build();
            var trigger = newTrigger().withSchedule(cronSchedule("0/2 * * * * ?")).build();
            scheduler.scheduleJob(jobDetail, trigger);
            factory.assertJobExecutedWithoutExceptions(jobDetail, 5);
        }
    }

    @Test
    void cronTriggerEquals() {
        var triggerName = "name1";
        var triggerGroup = "group1";
        var cronExpression = "0/2 * * * * ?";
        var trigger1 = newTrigger().withIdentity(triggerName, triggerGroup)
                .withSchedule(cronSchedule(cronExpression)).build();
        var trigger2 = newTrigger().withIdentity(triggerName, triggerGroup)
                .withSchedule(cronSchedule(cronExpression)).build();
        assertThat(trigger1).isEqualTo(trigger2);

        var trigger3 = newTrigger().withIdentity(triggerName, triggerGroup)
                .withSchedule(cronSchedule("0/10 * * * * ?")).build();
        assertThat(trigger3).isEqualTo(trigger1);
    }

    @Test
    void startTime() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class).withIdentity("jobDetail1").build();
            var triggerNum = 3;
            var triggers = IntStream.range(0, triggerNum).boxed().map(i -> newTrigger()
                            .withIdentity("Trigger" + i)
                            .withSchedule(cronSchedule("*/15 * * * * ?"))
                            .startAt(Date.from(Instant.now().plusSeconds(i * 5L)))
                            .build())
                    .collect(toSet());
            var simpleDateFormat = new SimpleDateFormat("hh:mm:ss.S");
            System.out.println("Trigger start times:\n" + triggers.stream()
                    .sorted(Comparator.comparing(Trigger::getStartTime))
                    .map(t -> simpleDateFormat.format(t.getStartTime()) + "-" + t.getKey())
                    .collect(Collectors.joining("\n")));
            scheduler.scheduleJob(jobDetail, triggers, false);
            factory.assertJobExecutedWithoutExceptions(jobDetail, actTimes -> actTimes >= triggerNum * 2, ofMinutes(1));
        }
    }

}
