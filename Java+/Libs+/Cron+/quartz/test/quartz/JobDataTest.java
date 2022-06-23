package quartz;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Configure a Job instance with JobData from JobDetails and Trigger.
 */
class JobDataTest {

    @Test
    void jobData() throws SchedulerException {
        var jobDetail = newJob(GreetingJob.class)
                .withIdentity("jobDetail1", "group1")
                .usingJobData(GreetingJob.PERSON_KEY, "John")
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData(GreetingJob.LOCATION_KEY, "Spain")
                .withSchedule(cronSchedule("0/2 * * * * ?"))
                .build();

        assertThat(GreetingJob.greeting).isNull();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        await().timeout(1, MINUTES).until(() -> GreetingJob.greeting != null);
        assertThat(GreetingJob.greeting).isEqualTo("Hello from Spain, John!");
        scheduler.shutdown(true);
    }

    public static class GreetingJob implements Job {
        public static final String PERSON_KEY = "person";
        public static final String LOCATION_KEY = "location";
        public static String greeting;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                var triggerDataMap = context.getTrigger().getJobDataMap();
                var jobDataMap = context.getJobDetail().getJobDataMap();
                var person = jobDataMap.getString(PERSON_KEY);
                var location = triggerDataMap.getString(LOCATION_KEY);

                var mergedDataMap = context.getMergedJobDataMap();
                assertThat(mergedDataMap.getString(PERSON_KEY)).isEqualTo(person);
                assertThat(mergedDataMap.getString(LOCATION_KEY)).isEqualTo(location);

                greeting = format("Hello from %s, %s!", location, person);
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
