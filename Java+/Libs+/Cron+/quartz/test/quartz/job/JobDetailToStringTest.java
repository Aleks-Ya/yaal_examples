package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class JobDetailToStringTest {
    @Test
    void jobDetailToString() throws SchedulerException {
        var jobDetail = newJob(EmptyJob.class)
                .withIdentity("jobName1", "jobGroup1")
                .build();
        assertThat(jobDetail).hasToString("JobDetail 'jobGroup1.jobName1':  " +
                "jobClass: 'quartz.EmptyJob concurrentExectionDisallowed: false " +
                "persistJobDataAfterExecution: false isDurable: false requestsRecovers: false");

        var startDate = Date.from(Instant.parse("2050-12-03T10:15:30.00Z"));
        var trigger = newTrigger()
                .withIdentity("triggerName1", "triggerGroup1")
                .startAt(startDate)
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        assertThat(jobDetail).hasToString("JobDetail 'jobGroup1.jobName1':  jobClass: 'quartz.EmptyJob " +
                "concurrentExectionDisallowed: false persistJobDataAfterExecution: false " +
                "isDurable: false requestsRecovers: false");

        scheduler.shutdown(true);
    }
}
