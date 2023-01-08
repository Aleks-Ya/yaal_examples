package quartz.exception;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import quartz.Factory;
import quartz.UniversalJob;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Unschedule all Triggers on exception.
 */
class UnscheduleOnExceptionTest {

    @Test
    @Disabled("don't work")
    void unscheduleAllTriggersTwoTriggers() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            var jobDataMap = new JobDataMap(Map.of(UniversalJob.EXCEPTION, new ArithmeticException("Divide by zero")));
            var jobDetail = newJob(UniversalJob.class).setJobData(jobDataMap).build();
            var trigger1 = newTrigger().withIdentity("trigger1")
                    .startAt(Date.from(Instant.now().plusMillis(500)))
                    .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(1))
                    .build();
            var trigger2 = newTrigger().withIdentity("trigger2")
                    .startAt(Date.from(Instant.now().plusMillis(5000)))
                    .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(10))
                    .build();
            scheduler.scheduleJob(jobDetail, Set.of(trigger1, trigger2), true);
            assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).asList().containsExactlyInAnyOrder(trigger1, trigger2);
//            await().timeout(5, SECONDS).until(() -> WaitJob.started);
//            await().timeout(5, SECONDS).untilAsserted(() -> assertThat(WaitJob.e2.getCause())
//                    .isInstanceOf(ArithmeticException.class)
//                    .hasMessage(DIVIDE_BY_ZERO));
//        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
            await().timeout(10, SECONDS).untilAsserted(() ->
                    assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).asList().containsOnly(trigger2));
//        await().timeout(10, SECONDS).untilAsserted(() ->
//                assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).allSatisfy(
//                        trigger -> assertThat(trigger.mayFireAgain()).isFalse()
//                ));
        }
    }
}
