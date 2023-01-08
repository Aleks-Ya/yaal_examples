package quartz.job.store;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import quartz.Factory;
import quartz.UniversalJob;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.WAIT_MILLIS;

@Disabled("work unstable")
class RAMJobStoreTest {

    private static void scheduleJobs(int jobCount, int stringLength, int waitMillis) throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobMap = new HashMap<JobDetail, Set<? extends Trigger>>();
            var random = new Random();
            for (int i = 0; i < jobCount; i++) {
                var bigString = IntStream.range(0, stringLength).boxed()
                        .map(num -> random.nextInt(0, 10))
                        .map(Integer::toUnsignedString)
                        .collect(Collectors.joining());
                var jobDetail = newJob(UniversalJob.class)
                        .withIdentity("jobDetail" + i, "group1")
                        .usingJobData("numbers", bigString)
                        .usingJobData(WAIT_MILLIS, waitMillis)
                        .build();
                var trigger = newTrigger()
                        .withIdentity("trigger" + i, "group1")
                        .startNow()
                        .build();
                jobMap.put(jobDetail, Set.of(trigger));
            }
            scheduler.scheduleJobs(jobMap, true);
            var jobsListener = factory.getJobsListener();
            await().timeout(2, MINUTES).until(() -> jobsListener.getWasExecutedJobs().size() == jobCount);
        }
    }

    @Test
    void outOfMemoryInJobMap() {
        assertThatThrownBy(() -> scheduleJobs(300, 100000, 1000))
                .isInstanceOf(OutOfMemoryError.class)
                .hasStackTraceContaining("HashMap.put");
    }

    @Test
    void outOfMemoryInScheduleJobs() {
        assertThatThrownBy(() -> scheduleJobs(3000, 1000, 500))
                .isInstanceOf(OutOfMemoryError.class)
                .hasStackTraceContaining("Arrays.copyOf");
    }
}
