package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;
import quartz.SingleResultListener;

import static org.quartz.JobBuilder.newJob;

/**
 * Trigger a Job without a Trigger.
 */
class RunJobWithoutTriggerTest {
    @Test
    void trigger() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class).storeDurably().build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.addJob(jobDetail, true);
        scheduler.triggerJob(jobDetail.getKey());
        SingleResultListener.<String>assign(scheduler, jobDetail).waitForFinish();
        scheduler.shutdown(true);
    }
}