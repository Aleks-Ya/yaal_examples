package quartz;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * The next fire happens before the previous fire is finished.
 */
class JobRunOverlapTest {

    @Test
    void cron() throws SchedulerException, InterruptedException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInMilliseconds(500))
                .build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        Thread.sleep(5000);
        scheduler.shutdown(true);
    }

    public static class WaitJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                System.out.printf("Start %s %s in %s\n",
                        context.getJobDetail().getKey(), context.getFireInstanceId(), Thread.currentThread().getName());
                Thread.sleep(2000);
                System.out.printf("Finish %s %s\n", context.getJobDetail().getKey(), context.getFireInstanceId());
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
