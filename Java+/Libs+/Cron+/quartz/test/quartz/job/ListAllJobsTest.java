package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import quartz.EmptyJob;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class ListAllJobsTest {
    @Test
    void listJobs() throws SchedulerException {
        var groupCount = 3;
        var jobInGroupCount = 5;
        var jobMap = new HashMap<JobDetail, Set<? extends Trigger>>();
        for (var groupIndex = 0; groupIndex < groupCount; groupIndex++) {
            var jobGroupName = "jobGroup" + groupIndex;
            var triggerGroupName = "triggerGroup" + groupIndex;
            for (var i = 0; i < jobInGroupCount; i++) {
                var jobDetail = newJob(EmptyJob.class)
                        .withIdentity("jobDetail" + i, jobGroupName)
                        .build();
                var trigger = newTrigger()
                        .withIdentity("trigger" + i, triggerGroupName)
                        .startAt(Date.from(Instant.now().plusMillis(10000)))
                        .build();
                jobMap.put(jobDetail, Set.of(trigger));
            }
        }

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJobs(jobMap, true);
        var actJobDetails = new ArrayList<JobDetail>();
        var groupNames = scheduler.getJobGroupNames();
        for (var groupName : groupNames) {
            var jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(groupName));
            for (var jobKey : jobKeys) {
                var jobDetails = scheduler.getJobDetail(jobKey);
                actJobDetails.add(jobDetails);
            }
        }
        assertThat(actJobDetails).containsAll(jobMap.keySet());
        scheduler.shutdown(true);
    }
}
