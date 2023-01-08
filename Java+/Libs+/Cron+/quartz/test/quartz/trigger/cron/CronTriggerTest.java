package quartz.trigger.cron;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;
import quartz.MultiResultListener;
import quartz.NowResultJob;
import quartz.ResultsJob;
import quartz.SingleResultListener;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class CronTriggerTest {
    @Test
    void cronSingleExecution() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class).build();
        var trigger = newTrigger().withSchedule(cronSchedule("0/2 * * * * ?")).build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        SingleResultListener.<String>assign(scheduler, jobDetail).waitForFinish();
        scheduler.shutdown(true);
    }

    @Test
    void cronMultipleExecutions() throws SchedulerException {
        var jobDetail = newJob(ResultsJob.class).build();
        var trigger = newTrigger().withSchedule(cronSchedule("0/2 * * * * ?")).build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        var results = MultiResultListener.<String>assign(scheduler, jobDetail).waitForResults(5);
        assertThat(results).containsExactly("Result0", "Result1", "Result2", "Result3", "Result4");
        scheduler.shutdown(true);
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
        var jobDetail = newJob(NowResultJob.class).build();
        var triggerNum = 10;
        var triggers = IntStream.range(1, triggerNum).boxed().map(i -> newTrigger()
                        .withIdentity("Trigger" + i)
                        .withSchedule(cronSchedule("0 * * * * ?"))
                        .startAt(Date.from(Instant.now().plusSeconds(i * 5L)))
                        .build())
                .collect(toSet());
        var simpleDateFormat = new SimpleDateFormat("hh:mm:ss.S");
        System.out.println("Trigger start times:\n" + triggers.stream()
                .sorted(Comparator.comparing(Trigger::getStartTime))
                .map(t -> simpleDateFormat.format(t.getStartTime()) + "-" + t.getKey())
                .collect(Collectors.joining("\n")));
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, triggers, false);
        var results = MultiResultListener.<String>assign(scheduler, jobDetail).waitForResults(triggerNum * 2);
        System.out.println("Result: " + String.join("\n", results));
        scheduler.shutdown(true);
    }

}
