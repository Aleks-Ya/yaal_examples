package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class GetJobDetailsTest {

    @Test
    void byName() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobName = "jobDetail1";
            var jobGroup = "group1";

            var expJobDetail = newJob(UniversalJob.class)
                    .withIdentity(jobName, jobGroup)
                    .build();
            var expTrigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(Date.from(Instant.now().plusMillis(1000)))
                    .build();

            scheduler.scheduleJob(expJobDetail, expTrigger);

            var jobKey = JobKey.jobKey(jobName, jobGroup);
            var actJobDetails = scheduler.getJobDetail(jobKey);
            var actTriggers = scheduler.getTriggersOfJob(jobKey);
            assertThat(actJobDetails).isEqualTo(expJobDetail);
            assertThat(actTriggers).asInstanceOf(list(Trigger.class)).containsExactly(expTrigger);
        }
    }

    @Test
    void jobAbsent() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobKey = JobKey.jobKey("AbsentName", "AbsentGroup");
            var jobDetail = scheduler.getJobDetail(jobKey);
            assertThat(jobDetail).isNull();
        }
    }

}
