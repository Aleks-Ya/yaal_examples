package quartz.trigger;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.UniversalJob.WAIT_MILLIS;

class OneJobManyTriggersTest {
    @Test
    void schedule() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDetail = newJob(UniversalJob.class)
                    .withIdentity("TheJobDetail")
                    .usingJobData(WAIT_MILLIS, 500)
                    .build();
            int triggerCount = 300;
            var triggers = IntStream.range(0, triggerCount).boxed()
                    .map(i -> newTrigger()
                            .withIdentity("trigger" + i)
                            .startNow()
                            .build())
                    .collect(Collectors.toSet());
            scheduler.scheduleJob(jobDetail, triggers, true);
            factory.assertJobExecutedWithoutExceptions(jobDetail, triggerCount);
        }
    }
}
