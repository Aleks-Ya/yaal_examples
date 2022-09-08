package quartz.exception;

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

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.KeyMatcher.keyEquals;

/**
 * Handle a Job exception from the mail program.
 */
class HandleExceptionTest {

    @Test
    void exception() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class).withIdentity("job1", "jobGroup1").build();
        var trigger1 = newTrigger().withIdentity("trigger1", "triggerGroup1").startNow().build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        var jobListener = new MyJobListener();
        scheduler.getListenerManager().addJobListener(jobListener, keyEquals(jobDetail.getKey()));
        scheduler.scheduleJob(jobDetail, trigger1);
        await().timeout(5, SECONDS).until(() -> WaitJob.e2 != null);
        scheduler.shutdown(true);
        assertThat(jobListener.jobWasExecuted).hasSize(1);
        var tuple = jobListener.jobWasExecuted.get(0);
        var jobExecutionContext = tuple.left();
        var exception = tuple.right();
        assertThat(jobExecutionContext.getJobDetail()).isEqualTo(jobDetail);
        assertThat(exception).isEqualTo(WaitJob.e2);

    }

    public static class WaitJob implements Job {
        static final String DIVIDE_BY_ZERO = "Divide by zero";
        static boolean started = false;
        static JobExecutionException e2;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            started = true;
            try {
                throw new ArithmeticException(DIVIDE_BY_ZERO);
            } catch (Exception e) {
                e2 = new JobExecutionException(e);
                throw e2;
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
