package quartz.exchange;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import quartz.Factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Return a result from a Job.
 */
class JobResultTest {

    @Test
    void result() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(ResultJob.class).build();
            var trigger = newTrigger().startNow().build();
            scheduler.scheduleJob(jobDetail, trigger);

            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
            var jobsListener = factory.getJobsListener();
            assertThat(jobsListener.getWasExecutedJobs().get(0).getLeft().getResult()).isEqualTo(42);
        }
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
}
