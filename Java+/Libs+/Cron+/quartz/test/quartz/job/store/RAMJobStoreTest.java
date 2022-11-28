package quartz.job.store;

import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

class RAMJobStoreTest {

    private static void scheduleJobs(int jobCount) throws SchedulerException {
        var jobMap = new HashMap<JobDetail, Set<? extends Trigger>>();
        for (int i = 0; i < jobCount; i++) {
            var jobDetail = newJob(WaitJob.class)
                    .withIdentity("jobDetail" + i, "group1")
                    .usingJobData("numbers", IntStream.range(0, 1000).boxed().map(Integer::toUnsignedString).collect(Collectors.joining()))
                    .build();
            var trigger = newTrigger()
                    .withIdentity("trigger" + i, "group1")
                    .startNow()
                    .build();
            jobMap.put(jobDetail, Set.of(trigger));
        }

        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJobs(jobMap, true);
        scheduler.shutdown(true);
    }

    @Test
    void outOfMemoryInJobMap() {
        assertThatThrownBy(() -> scheduleJobs(3000000))
                .isInstanceOf(OutOfMemoryError.class)
                .hasStackTraceContaining("HashMap.put");
    }

    @Test
    void outOfMemoryInScheduleJobs() {
        assertThatThrownBy(() -> scheduleJobs(300000))
                .isInstanceOf(OutOfMemoryError.class)
                .hasStackTraceContaining("Arrays.copyOf");
    }

    public static class WaitJob implements Job {

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                Thread.sleep(500);
                System.out.println("Job is done: " + context.getJobDetail().getKey());
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }
}
