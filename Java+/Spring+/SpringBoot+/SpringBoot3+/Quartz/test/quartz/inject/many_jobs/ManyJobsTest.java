package quartz.inject.many_jobs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuartzConfig.class, SampleJobService.class})
class ManyJobsTest {

    @Autowired
    private SampleJobService sampleJobService;

    @Autowired
    private Scheduler scheduler;

    @Test
    void test() throws SchedulerException {
        var jobDetail1 = newJob(SampleJob.class).withIdentity("jobDetail1", "group1").build();
        var trigger1 = newTrigger().withIdentity("trigger1", "group1").startNow().build();

        var jobDetail2 = newJob(SampleJob.class).withIdentity("jobDetail2", "group1").build();
        var trigger2 = newTrigger().withIdentity("trigger2", "group1").startNow().build();

        scheduler.scheduleJob(jobDetail1, trigger1);
        scheduler.scheduleJob(jobDetail2, trigger2);

        await().untilAsserted(() -> assertThat(sampleJobService.getInvocationCounter()).isEqualTo(2));
    }
}