package quartz.trigger;

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

class ListAllTriggersTest {
    @Test
    void listTriggers() throws SchedulerException {
        var groupCount = 3;
        var jobInGroupCount = 5;
        var expAllTriggers = new ArrayList<Trigger>();
        var jobMap = new HashMap<JobDetail, Set<? extends Trigger>>();
        for (var groupIndex = 0; groupIndex < groupCount; groupIndex++) {
            var jobGroupName = "jobGroup" + groupIndex;
            var triggerGroupName = "triggerGroup" + groupIndex;
            for (var i = 0; i < jobInGroupCount; i++) {
                var jobDetail = newJob(EmptyJob.class)
                        .withIdentity("jobDetail" + i, jobGroupName)
                        .build();
                var trigger1 = newTrigger()
                        .withIdentity("trigger1" + i, triggerGroupName)
                        .startAt(Date.from(Instant.now().plusMillis(10000)))
                        .build();
                var trigger2 = newTrigger()
                        .withIdentity("trigger2" + i, triggerGroupName)
                        .startAt(Date.from(Instant.now().plusMillis(20000)))
                        .build();
                expAllTriggers.add(trigger1);
                expAllTriggers.add(trigger2);
                jobMap.put(jobDetail, Set.of(trigger1, trigger2));
            }
        }

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJobs(jobMap, true);

        var allTriggers = new ArrayList<Trigger>();
        var groupNames = scheduler.getJobGroupNames();
        for (var groupName : groupNames) {
            var jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(groupName));
            for (var jobKey : jobKeys) {
                var jobTriggers = scheduler.getTriggersOfJob(jobKey);
                allTriggers.addAll(jobTriggers);
            }
        }
        assertThat(allTriggers).containsExactlyInAnyOrderElementsOf(expAllTriggers);

        scheduler.shutdown(true);
    }
}
