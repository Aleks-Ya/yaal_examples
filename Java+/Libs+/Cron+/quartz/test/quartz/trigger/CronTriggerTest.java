package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;
import quartz.MultiResultListener;
import quartz.ResultsJob;
import quartz.SingleResultListener;

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

}
