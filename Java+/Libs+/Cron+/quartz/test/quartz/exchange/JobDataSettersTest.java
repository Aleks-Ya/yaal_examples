package quartz.exchange;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import quartz.Factory;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Inject JobData with setXXX() methods.
 */
class JobDataSettersTest {

    @Test
    void jobData() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(GreetingJob.class)
                    .usingJobData("person", "John")
                    .build();
            var trigger = newTrigger()
                    .usingJobData("location", "Spain")
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            factory.assertJobExecutedWithoutExceptions(jobDetail, 1);
            var jobsListener = factory.getJobsListener();
            var result = jobsListener.getWasExecutedJobs().get(0).getLeft().getResult();
            assertThat(result).isEqualTo("Hello from Spain, John!");
        }
    }

    public static class GreetingJob implements Job {
        private String person;
        private String location;

        public void setPerson(String person) {
            this.person = person;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                context.setResult(format("Hello from %s, %s!", location, person));
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
