package quartz.exception;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.Date;
import java.time.Instant;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static quartz.exception.UnscheduleOnExceptionTest.WaitJob.DIVIDE_BY_ZERO;

/**
 * Unschedule all Triggers on exception.
 */
class UnscheduleOnExceptionTest {

    @Test
    @Disabled("don't work")
    void unscheduleAllTriggersTwoTriggers() throws SchedulerException {
        var jobDetail = newJob(WaitJob.class).build();
        var trigger1 = newTrigger().withIdentity("trigger1")
                .startAt(Date.from(Instant.now().plusMillis(500)))
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(1))
                .build();
        var trigger2 = newTrigger().withIdentity("trigger2")
                .startAt(Date.from(Instant.now().plusMillis(5000)))
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(10))
                .build();
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, Set.of(trigger1, trigger2), true);
        assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).asList().containsExactlyInAnyOrder(trigger1, trigger2);
        await().timeout(5, SECONDS).until(() -> WaitJob.started);
        await().timeout(5, SECONDS).untilAsserted(() -> assertThat(WaitJob.e2.getCause())
                .isInstanceOf(ArithmeticException.class)
                .hasMessage(DIVIDE_BY_ZERO));
//        assertThat(scheduler.checkExists(jobDetail.getKey())).isFalse();
        await().timeout(10, SECONDS).untilAsserted(() ->
                assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).asList().containsOnly(trigger2));
//        await().timeout(10, SECONDS).untilAsserted(() ->
//                assertThat(scheduler.getTriggersOfJob(jobDetail.getKey())).allSatisfy(
//                        trigger -> assertThat(trigger.mayFireAgain()).isFalse()
//                ));
        scheduler.shutdown(true);
    }

    public static class WaitJob implements Job {
        static final String DIVIDE_BY_ZERO = "Divide by zero";
        static boolean started = false;
        static JobExecutionException e2;

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            started = true;
            try {
                throw new ArithmeticException(DIVIDE_BY_ZERO);
            } catch (Exception e) {
                e2 = new JobExecutionException("abc", e);
                e2.setUnscheduleAllTriggers(true);
//                e2.setUnscheduleFiringTrigger(true);
                System.out.println("Throwing exception: " + e2);
                throw e2;
            }
        }
    }
}
