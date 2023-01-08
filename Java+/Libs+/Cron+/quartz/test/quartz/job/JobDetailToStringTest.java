package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class JobDetailToStringTest {
    @Test
    void jobDetailToString() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("jobName1", "jobGroup1")
                    .build();
            assertThat(jobDetail).hasToString("JobDetail 'jobGroup1.jobName1':  " +
                    "jobClass: 'quartz.UniversalJob concurrentExectionDisallowed: false " +
                    "persistJobDataAfterExecution: false isDurable: false requestsRecovers: false");

            var startDate = Date.from(Instant.parse("2050-12-03T10:15:30.00Z"));
            var trigger = newTrigger()
                    .withIdentity("triggerName1", "triggerGroup1")
                    .startAt(startDate)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            assertThat(jobDetail).hasToString("JobDetail 'jobGroup1.jobName1':  jobClass: 'quartz.UniversalJob " +
                    "concurrentExectionDisallowed: false persistJobDataAfterExecution: false " +
                    "isDurable: false requestsRecovers: false");
        }
    }
}
