package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.plugins.history.LoggingJobHistoryPlugin;
import org.quartz.plugins.history.LoggingTriggerHistoryPlugin;
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
 * Log trigger fires, misfires, completes.
 */
class TriggerHistoryPluginTest {

    @Test
    void logTriggers() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler(Map.of(
                    "org.quartz.threadPool.threadCount", "1",
                    "org.quartz.plugin.triggHistory.class", LoggingTriggerHistoryPlugin.class.getName()));
            var repeatCount = 3;
            var jobDetail = newJob(UniversalJob.class).withIdentity("jobDetails1").build();
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
