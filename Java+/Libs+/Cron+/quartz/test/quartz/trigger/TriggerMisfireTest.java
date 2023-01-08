package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.listeners.TriggerListenerSupport;
import quartz.EmptyJob;
import quartz.MultiResultListener;
import quartz.ResultsJob;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

class TriggerMisfireTest {
    @Test
    void busyThreadPool() throws SchedulerException {
        var schedulerProperties = new Properties();
        schedulerProperties.setProperty("org.quartz.threadPool.threadCount", "1");
        var factory = new StdSchedulerFactory(schedulerProperties);
        var scheduler = factory.getScheduler();

        var jobGroup = "TheJobGroup";
        var jobDetail1 = newJob(ResultsJob.class)
                .withIdentity("jobDetail1", jobGroup)
                .build();
        var jobDetail2 = newJob(ResultsJob.class)
                .withIdentity("jobDetail2", jobGroup)
                .build();
        var triggerGroup = "TheTriggerGroup";
        var trigger1 = newTrigger()
                .withIdentity("trigger1", triggerGroup)
                .withSchedule(repeatSecondlyForever(5).withMisfireHandlingInstructionIgnoreMisfires())
                .startNow()
                .build();
        var trigger2 = newTrigger()
                .withIdentity("trigger2", triggerGroup)
                .withSchedule(repeatSecondlyForever(5).withMisfireHandlingInstructionIgnoreMisfires())
                .startNow()
                .build();

        scheduler.start();
        scheduler.scheduleJob(jobDetail1, trigger1);
        scheduler.scheduleJob(jobDetail2, trigger2);
        MultiResultListener.<String>assign(scheduler, jobDetail1).waitForResults(5);
        MultiResultListener.<String>assign(scheduler, jobDetail2).waitForResults(5);
        scheduler.shutdown(true);
    }

    @Test
    void startAtPast() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class)
                .withIdentity("jobDetail1", "TheJobGroup")
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "TheTriggerGroup")
                .withSchedule(simpleSchedule().withMisfireHandlingInstructionFireNow())
                .startAt(new Date(Instant.now().minusSeconds(100).toEpochMilli()))
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        var misfireListener = new MisfireListener();
        scheduler.getListenerManager().addTriggerListener(misfireListener);
        scheduler.scheduleJob(jobDetail, trigger);
        await().until(() -> !misfireListener.getMisfiredTriggers().isEmpty());
        scheduler.shutdown(true);
    }

    static class MisfireListener extends TriggerListenerSupport {
        private final List<Trigger> misfiredTriggers = new ArrayList<>();

        @Override
        public String getName() {
            return MisfireListener.class.getSimpleName();
        }

        @Override
        public void triggerMisfired(Trigger trigger) {
            System.out.println("Trigger misfired: " + trigger);
            misfiredTriggers.add(trigger);
        }

        public List<Trigger> getMisfiredTriggers() {
            return misfiredTriggers;
        }
    }

}
