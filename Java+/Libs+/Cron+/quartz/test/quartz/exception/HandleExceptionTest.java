package quartz.exception;

import org.junit.jupiter.api.Test;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.EXCEPTION;

/**
 * Handle a Job exception from the mail program.
 */
class HandleExceptionTest {
    @Test
    void exception() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var expException = new ArithmeticException("Divide by zero");
            var jobDataMap = new JobDataMap(Map.of(EXCEPTION, expException));
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobDetail1")
                    .setJobData(jobDataMap)
                    .build();
            var trigger = newTrigger().withIdentity("trigger1").startNow().build();
            scheduler.scheduleJob(jobDetail, trigger);
            var jobsListener = factory.getJobsListener();
            await().until(() -> jobsListener.getTimesJobWasExecuted(jobDetail) == 1);
            var actException = jobsListener.getWasExecutedJobs().get(0).getRight().getUnderlyingException();
            assertThat(actException).isEqualTo(expException);
        }
    }
}
