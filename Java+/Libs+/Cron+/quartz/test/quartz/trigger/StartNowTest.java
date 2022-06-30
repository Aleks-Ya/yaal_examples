package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;
import quartz.SingleResultListener;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class StartNowTest {
    @Test
    void startNow() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class).build();
        var trigger = newTrigger().startNow().build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        SingleResultListener.<String>assign(scheduler, jobDetail).waitForFinish(1000);
        scheduler.shutdown(true);
    }
}
