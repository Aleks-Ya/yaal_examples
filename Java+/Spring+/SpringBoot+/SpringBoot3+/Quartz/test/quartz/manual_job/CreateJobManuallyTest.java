package quartz.manual_job;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Define JobDetails and Trigger manually.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuartzConfig.class, DownloadingJob.class})
class CreateJobManuallyTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    void test() throws SchedulerException {
        var jobDetail = JobBuilder.newJob(DownloadingJob.class)
                .withIdentity("jobDetail1", "group1")
                .usingJobData(DownloadingJob.DELAY_MS_KEY, 500)
                .build();
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        await().timeout(5, SECONDS).until(() -> DownloadingJob.done);
    }
}