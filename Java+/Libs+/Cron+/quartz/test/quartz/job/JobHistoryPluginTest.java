package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.plugins.history.LoggingJobHistoryPlugin;
import quartz.Factory;
import quartz.UniversalJob;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Log job history with {@link LoggingJobHistoryPlugin}.
 * Do not log trigger misfires.
 */
class JobHistoryPluginTest {

    @Test
    void logJobs() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler(Map.of(
                    "org.quartz.threadPool.threadCount", "1",
                    "org.quartz.plugin.jobHistory.class", LoggingJobHistoryPlugin.class.getName()));
            var repeatCount = 3;
            var jobDetail = newJob(UniversalJob.class).withIdentity("jobDetail1").build();
            var trigger = newTrigger()
                    .withIdentity("trigger1")
                    .withSchedule(simpleSchedule()
                            .withIntervalInMilliseconds(1000)
                            .withRepeatCount(repeatCount)).build();
            scheduler.scheduleJob(jobDetail, trigger);

            var jobDetailMisfire = newJob(UniversalJob.class)
                    .withIdentity("jobDetail2")
                    .build();
            var triggerMisfire = newTrigger()
                    .withIdentity("trigger2")
                    .withSchedule(simpleSchedule().withMisfireHandlingInstructionFireNow())
                    .startAt(new Date(Instant.now().minusSeconds(100).toEpochMilli()))
                    .build();
            scheduler.scheduleJob(jobDetailMisfire, triggerMisfire);
            factory.assertJobExecutedWithoutExceptions(jobDetail, repeatCount);
        }
    }

}
