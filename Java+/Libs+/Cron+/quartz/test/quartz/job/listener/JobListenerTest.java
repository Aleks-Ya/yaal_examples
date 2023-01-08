package quartz.job.listener;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import util.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.KeyMatcher.keyEquals;

/**
 * Use a JobListener.
 */
class JobListenerTest {

    @Test
    void listener() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class)
                .withIdentity("jobDetail1", "group1")
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        var jobListener = new MyJobListener();
        scheduler.getListenerManager().addJobListener(jobListener, keyEquals(jobDetail.getKey()));
        scheduler.scheduleJob(jobDetail, trigger);
        await().timeout(30, SECONDS).until(() -> WaitJob.done);
        scheduler.shutdown(true);
        assertThat(jobListener.jobToBeExecuted).hasSize(1);
        assertThat(jobListener.jobExecutionVetoed).isEmpty();
        assertThat(jobListener.jobWasExecuted).hasSize(1);
    }

    public static class WaitJob implements Job {
        static boolean done = false;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                Thread.sleep(500);
                done = true;
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }

    private static class MyJobListener implements JobListener {
        final List<JobExecutionContext> jobToBeExecuted = new ArrayList<>();
        final List<JobExecutionContext> jobExecutionVetoed = new ArrayList<>();
        final List<Tuple2<JobExecutionContext, JobExecutionException>> jobWasExecuted = new ArrayList<>();

        @Override
        public String getName() {
            return MyJobListener.class.getSimpleName();
        }

        @Override
        public void jobToBeExecuted(JobExecutionContext context) {
            jobToBeExecuted.add(context);
        }

        @Override
        public void jobExecutionVetoed(JobExecutionContext context) {
            jobExecutionVetoed.add(context);
        }

        @Override
        public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
            jobWasExecuted.add(Tuple2.of(context, jobException));
        }
    }
}
