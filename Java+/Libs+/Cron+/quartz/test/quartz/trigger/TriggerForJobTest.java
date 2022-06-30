package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;
import quartz.SingleResultListener;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Specify JobDetails in Trigger.
 */
class TriggerForJobTest {
    @Test
    void schedule() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class).storeDurably().build();
        var trigger = newTrigger().forJob(jobDetail).startNow().build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.addJob(jobDetail, true);
        scheduler.scheduleJob(trigger);
        SingleResultListener.<String>assign(scheduler, jobDetail).waitForFinish();
        scheduler.shutdown(true);
    }
}
