package quartz.job.listener;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.plugins.history.LoggingJobHistoryPlugin;
import quartz.MultiResultListener;
import quartz.ResultsJob;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Log job history with {@link LoggingJobHistoryPlugin}.
 */
class LoggingJobHistoryPluginTest {

    @Test
    void logJobs() throws SchedulerException {
        var repeatCount = 3;
        var jobDetail = newJob(ResultsJob.class).build();
        var trigger = newTrigger().withSchedule(simpleSchedule()
                .withIntervalInMilliseconds(1000)
                .withRepeatCount(repeatCount)).build();
        var schedulerProperties = new Properties();
        schedulerProperties.setProperty("org.quartz.threadPool.threadCount", "1");
        schedulerProperties.setProperty("org.quartz.plugin.jobHistory.class", LoggingJobHistoryPlugin.class.getName());

        var factory = new StdSchedulerFactory(schedulerProperties);
        var scheduler = factory.getScheduler();

        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);

        var results = MultiResultListener.<String>assign(scheduler, jobDetail).waitForResults(repeatCount);
        assertThat(results).containsExactly("Result0", "Result1", "Result2");
        scheduler.shutdown(true);
    }

}
