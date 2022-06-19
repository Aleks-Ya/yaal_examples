package quartz;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.quartz.TriggerBuilder.newTrigger;

class RunManyJobsTest {
    private static final int JOB_COUNT = 300;

    @Test
    void schedule() throws SchedulerException {
        var jobMap = new HashMap<JobDetail, Set<? extends Trigger>>();
        for (int i = 0; i < JOB_COUNT; i++) {
            var jobDetail = JobBuilder.newJob(WaitJob.class)
                    .withIdentity("jobDetail" + i, "group1")
                    .build();
            var trigger = newTrigger()
                    .withIdentity("trigger" + i, "group1")
                    .startNow()
                    .build();
            jobMap.put(jobDetail, Set.of(trigger));
        }

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJobs(jobMap, true);
        await().timeout(1, MINUTES).until(() -> WaitJob.latch.await(10, SECONDS));
        scheduler.shutdown(true);
    }

    public static class WaitJob implements Job {
        static final CountDownLatch latch = new CountDownLatch(JOB_COUNT);

        @Override
        public void execute(JobExecutionContext context) {
            try {
                Thread.sleep(500);
                System.out.println("Job is done: " + context.getJobDetail().getKey());
                latch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
