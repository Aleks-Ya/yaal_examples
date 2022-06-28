package quartz.exchange;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.SingleResultListener;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.exchange.JobDataContextTest.GreetingJob.LOCATION_KEY;
import static quartz.exchange.JobDataContextTest.GreetingJob.PERSON_KEY;

/**
 * Configure a Job instance with JobData from JobDetails and Trigger (get JobData from JobExecutionContext).
 */
class JobDataContextTest {

    @Test
    void jobData() throws SchedulerException {
        //Put an Object
        var map = new JobDataMap();
        map.put(PERSON_KEY, new Person(1L, "John"));
        var jobDetail = newJob(GreetingJob.class)
                .usingJobData(map)
                .build();
        //Put a primitive
        var trigger = newTrigger()
                .usingJobData(LOCATION_KEY, "Spain")
                .startNow()
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        var listener = SingleResultListener.<String>assign(scheduler, jobDetail);
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        assertThat(listener.waitForResult()).isEqualTo("Hello from Spain, John!");
        scheduler.shutdown(true);
    }

    public static class GreetingJob implements Job {
        public static final String PERSON_KEY = "person";
        public static final String LOCATION_KEY = "location";

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                var triggerDataMap = context.getTrigger().getJobDataMap();
                var jobDataMap = context.getJobDetail().getJobDataMap();
                var person = (Person) jobDataMap.get(PERSON_KEY);
                var location = triggerDataMap.getString(LOCATION_KEY);

                var mergedDataMap = context.getMergedJobDataMap();
                assertThat(mergedDataMap.get(PERSON_KEY)).isEqualTo(person);
                assertThat(mergedDataMap.getString(LOCATION_KEY)).isEqualTo(location);

                context.setResult(format("Hello from %s, %s!", location, person.name()));
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }

    record Person(Long id, String name) {
    }
}
