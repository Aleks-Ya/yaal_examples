package quartz.job.listener;

import org.junit.jupiter.api.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;
import quartz.Factory;
import quartz.UniversalJob;
import util.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.KeyMatcher.keyEquals;
import static quartz.UniversalJob.WAIT_MILLIS;

/**
 * Use a JobListenerSupport.
 */
class JobListenerSupportTest {

    @Test
    void listener() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1", "group1")
                    .usingJobData(WAIT_MILLIS, 500)
                    .build();
            var trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .build();

            var jobListener = new MyJobListener();
            scheduler.getListenerManager().addJobListener(jobListener, keyEquals(jobDetail.getKey()));
            scheduler.scheduleJob(jobDetail, trigger);
            await().timeout(30, SECONDS).untilAsserted(() -> {
                assertThat(jobListener.jobToBeExecuted).hasSize(1);
                assertThat(jobListener.jobExecutionVetoed).isEmpty();
                assertThat(jobListener.jobWasExecuted).hasSize(1);
            });
        }
    }

    private static class MyJobListener extends JobListenerSupport {
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
