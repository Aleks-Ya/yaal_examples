package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import quartz.EmptyJob;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class GetJobDetailsTest {

    @Test
    void byName() throws SchedulerException {
        var jobName = "jobDetail1";
        var jobGroup = "group1";

        var expJobDetail = newJob(EmptyJob.class)
                .withIdentity(jobName, jobGroup)
                .build();
        var expTrigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(Date.from(Instant.now().plusMillis(1000)))
                .build();

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(expJobDetail, expTrigger);

        var jobKey = JobKey.jobKey(jobName, jobGroup);
        var actJobDetails = scheduler.getJobDetail(jobKey);
        var actTriggers = scheduler.getTriggersOfJob(jobKey);
        assertThat(actJobDetails).isEqualTo(expJobDetail);
        assertThat(actTriggers).asInstanceOf(list(Trigger.class)).containsExactly(expTrigger);

        scheduler.shutdown(true);
    }

    @Test
    void jobAbsent() throws SchedulerException {
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        var jobKey = JobKey.jobKey("AbsentName", "AbsentGroup");
        var jobDetail = scheduler.getJobDetail(jobKey);
        assertThat(jobDetail).isNull();

        scheduler.shutdown(true);
    }

}
