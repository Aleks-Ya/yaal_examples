package quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import util.RandomUtil;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.quartz.impl.StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME;

public class Factory implements AutoCloseable {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss.S");
    private Scheduler scheduler;
    private JobsListener jobsListener;
    private TriggersListener triggersListener;

    public Scheduler newScheduler(Map<String, String> propertiesOverride, boolean start) {
        try {
            var schedulerName = "Scheduler" + RandomUtil.randomIntPositive();
            var schedulerProperties = new Properties();
            schedulerProperties.setProperty(PROP_SCHED_INSTANCE_NAME, schedulerName);
            schedulerProperties.setProperty("org.quartz.threadPool.threadCount", "10");
            schedulerProperties.putAll(propertiesOverride);
            var stdSchedulerFactory = new StdSchedulerFactory(schedulerProperties);
            scheduler = stdSchedulerFactory.getScheduler();
            jobsListener = JobsListener.assign(scheduler);
            triggersListener = TriggersListener.assign(scheduler);
            if (start) {
                scheduler.start();
            }
            System.out.printf("Scheduler created: name='%s', now='%s'\n",
                    scheduler.getSchedulerName(), LocalTime.now().format(formatter));
            return scheduler;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public Scheduler newScheduler(Map<String, String> propertiesOverride) {
        return newScheduler(propertiesOverride, true);
    }

    public Scheduler newScheduler() {
        return newScheduler(Map.of(), true);
    }

    public Scheduler newSchedulerNotStart() {
        return newScheduler(Map.of(), false);
    }

    public JobsListener getJobsListener() {
        return jobsListener;
    }

    public TriggersListener getTriggersListener() {
        return triggersListener;
    }

    public void assertJobExecutedWithoutExceptions(JobDetail jobDetail, int times) {
        assertJobExecutedWithoutExceptions(jobDetail, actTimes -> actTimes == times, Duration.ofSeconds(60));
    }

    public void assertJobExecutedWithoutExceptions(JobDetail jobDetail, Predicate<Integer> times, Duration timeout) {
        await().timeout(timeout).until(() -> times.test(getJobsListener().getTimesJobWasExecuted(jobDetail)));
        assertThat(getJobsListener().wasNoExceptions()).isTrue();
    }

    @Override
    public void close() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
