package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.WAIT_MILLIS;

class TriggerMisfireTest {
    @Test
    void startAtPast() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1")
                    .build();
            var trigger = newTrigger()
                    .withIdentity("trigger1")
                    .withSchedule(simpleSchedule().withMisfireHandlingInstructionFireNow())
                    .startAt(new Date(Instant.now().minusSeconds(100).toEpochMilli()))
                    .build();
            var triggersListener = factory.getTriggersListener();
            scheduler.scheduleJob(jobDetail, trigger);
            await().until(() -> triggersListener.getMisfiredTriggers().contains(trigger));
        }
    }

    @Test
    void busyThreadPool() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler(Map.of("org.quartz.threadPool.threadCount", "1"));
            var jobDetail1 = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1")
                    .usingJobData(WAIT_MILLIS, 5000)
                    .build();
            var jobDetail2 = newJob(UniversalJob.class)
                    .withIdentity("jobDetail2")
                    .usingJobData(WAIT_MILLIS, 5000)
                    .build();
            var trigger1 = newTrigger()
                    .withIdentity("trigger1")
                    .withSchedule(repeatSecondlyForever(5).withMisfireHandlingInstructionFireNow())
                    .startNow()
                    .build();
            var trigger2 = newTrigger()
                    .withIdentity("trigger2")
                    .withSchedule(repeatSecondlyForever(5).withMisfireHandlingInstructionFireNow())
                    .startNow()
                    .build();

            var triggersListener = factory.getTriggersListener();

            scheduler.scheduleJob(jobDetail1, trigger1);
            scheduler.scheduleJob(jobDetail2, trigger2);
            await().timeout(1, MINUTES).until(() -> triggersListener.getMisfiredTriggers().size() > 1);
        }
    }

    @Test
    void schedulerWasPaused() throws SchedulerException, InterruptedException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler(Map.of("org.quartz.jobStore.misfireThreshold", "1000"));
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1")
                    .build();
            var trigger = newTrigger()
                    .withIdentity("trigger1")
                    .withSchedule(repeatSecondlyForever(15).withMisfireHandlingInstructionFireNow())
                    .startAt(new Date(Instant.now().plusSeconds(1).toEpochMilli()))
                    .build();
            var triggersListener = factory.getTriggersListener();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.pauseTrigger(trigger.getKey());
            Thread.sleep(2000);
            scheduler.resumeTrigger(trigger.getKey());
            await().until(() -> triggersListener.getMisfiredTriggers().contains(trigger));
        }
    }
}
