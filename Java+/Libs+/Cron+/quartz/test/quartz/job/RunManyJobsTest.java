package quartz.job;

import org.junit.jupiter.api.Test;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import quartz.Factory;
import quartz.UniversalJob;

import java.util.HashMap;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.WAIT_MILLIS;

class RunManyJobsTest {
    private static final int JOB_COUNT = 200;

    @Test
    void schedule() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobMap = new HashMap<JobDetail, Set<? extends Trigger>>();
            for (int i = 0; i < JOB_COUNT; i++) {
                var jobDetail = newJob(UniversalJob.class)
                        .withIdentity("jobDetail" + i, "group1")
                        .usingJobData(WAIT_MILLIS, 300)
                        .build();
                var trigger = newTrigger()
                        .withIdentity("trigger" + i, "group1")
                        .startNow()
                        .build();
                jobMap.put(jobDetail, Set.of(trigger));
            }

            scheduler.scheduleJobs(jobMap, true);
            var jobsListener = factory.getJobsListener();
            await().timeout(2, MINUTES).until(() -> jobsListener.getWasExecutedJobs().size() == JOB_COUNT);
        }
    }
}
