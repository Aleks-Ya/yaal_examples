package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class OneJobManyTriggersTest {
    private static final int TRIGGER_COUNT = 300;
    private static final String NUMBER_DATA_KEY = "number";

    @Test
    void schedule() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("TheJobDetail", "TheJobGroup")
                .build();
        var triggers = IntStream.range(0, TRIGGER_COUNT).boxed()
                .map(i -> newTrigger()
                        .withIdentity("trigger" + i, "group1")
                        .startNow()
                        .usingJobData(NUMBER_DATA_KEY, i)
                        .build())
                .collect(Collectors.toSet());

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, triggers, true);
        await().timeout(1, MINUTES).until(() -> WaitJob.latch.await(10, SECONDS));
        scheduler.shutdown(true);
    }

    public static class WaitJob implements Job {
        static final CountDownLatch latch = new CountDownLatch(TRIGGER_COUNT);

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                Thread.sleep(500);
                System.out.printf("Job is done: latch=%d, job=%s, trigger=%s, number=%d\n", latch.getCount(),
                        context.getJobDetail().getKey(), context.getTrigger().getKey(),
                        context.getMergedJobDataMap().getInt(NUMBER_DATA_KEY));
                latch.countDown();
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
