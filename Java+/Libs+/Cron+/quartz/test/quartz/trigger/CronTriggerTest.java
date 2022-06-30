package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;
import quartz.SingleResultListener;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class CronTriggerTest {
    @Test
    void cron() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class).build();
        var trigger = newTrigger().withSchedule(cronSchedule("0/2 * * * * ?")).build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        SingleResultListener.<String>assign(scheduler, jobDetail).waitForFinish();
        scheduler.shutdown(true);
    }
}
