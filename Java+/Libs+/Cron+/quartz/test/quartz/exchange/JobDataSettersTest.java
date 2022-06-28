package quartz.exchange;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.SingleResultListener;

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
        var jobDetail = newJob(GreetingJob.class)
                .usingJobData("person", "John")
                .build();
        var trigger = newTrigger()
                .usingJobData("location", "Spain")
                .startNow()
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        var listener = SingleResultListener.<String>assign(scheduler, jobDetail);
        assertThat(listener.waitForResult()).isEqualTo("Hello from Spain, John!");
        scheduler.shutdown(true);
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
