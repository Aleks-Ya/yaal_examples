package quartz;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.await;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

class CronTriggerTest {

    @Test
    void cron() throws SchedulerException {
        var jobDetail = JobBuilder.newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0/2 * * * * ?"))
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        await().timeout(1, MINUTES).until(() -> WaitJob.executed > 2);
        scheduler.shutdown(true);
    }

    public static class WaitJob implements Job {
        static int executed = 0;

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Job is done: " + context.getJobDetail().getKey() + " " + context.getFireTime());
            executed++;
        }
    }
}
