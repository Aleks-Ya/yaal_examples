package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;
import quartz.MultiResultListener;
import quartz.ResultsJob;
import quartz.SingleResultListener;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
        var startAt = Date.from(Instant.now().plus(1, ChronoUnit.SECONDS));
        System.out.println("Start at: " + startAt);
        var trigger = newTrigger()
                .withSchedule(cronSchedule("0/2 * * * * ?"))
                .startAt(startAt)
                .build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        var results = MultiResultListener.<String>assign(scheduler, jobDetail).waitForResults(3);
        System.out.println("Result: " + results);
        scheduler.shutdown(true);
    }

    public static class NowResultJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            context.setResult(Instant.now());
        }
    }

}
