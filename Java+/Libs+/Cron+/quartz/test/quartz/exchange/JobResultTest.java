package quartz.exchange;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.listeners.JobListenerSupport;
import util.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.KeyMatcher.keyEquals;

/**
 * Return a result from a Job.
 */
class JobResultTest {

    @Test
    void result() throws SchedulerException {
        var jobDetail = newJob(ResultJob.class).build();
        var trigger = newTrigger().startNow().build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        var jobListener = new MyJobListener();
        scheduler.getListenerManager().addJobListener(jobListener, keyEquals(jobDetail.getKey()));
        scheduler.scheduleJob(jobDetail, trigger);

        await().until(() -> !jobListener.jobWasExecuted.isEmpty());
        assertThat(jobListener.jobWasExecuted.get(0).left().getResult()).isEqualTo(42);
        scheduler.shutdown(true);
    }

    public static class ResultJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                Thread.sleep(500);
                System.out.println("Job is done: " + context.getJobDetail().getKey());
                context.setResult(42);
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }

    private static class MyJobListener extends JobListenerSupport {
        final List<Tuple2<JobExecutionContext, JobExecutionException>> jobWasExecuted = new ArrayList<>();

        @Override
        public String getName() {
            return MyJobListener.class.getSimpleName();
        }

        @Override
        public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
            jobWasExecuted.add(Tuple2.of(context, jobException));
        }
    }
}
