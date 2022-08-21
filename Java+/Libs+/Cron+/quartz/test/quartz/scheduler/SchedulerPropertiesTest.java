package quartz.scheduler;

import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.impl.StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME;

/**
 * Configure {@link Scheduler} properties in tests.
 */
class SchedulerPropertiesTest {

    @Test
    void scheduler() throws SchedulerException {
        var expSchedulerName = "instance1";

        var schedulerProperties = new Properties();
        schedulerProperties.setProperty(PROP_SCHED_INSTANCE_NAME, expSchedulerName);
        schedulerProperties.setProperty("org.quartz.threadPool.threadCount", "1");

        var factory = new StdSchedulerFactory(schedulerProperties);
        var scheduler = factory.getScheduler();
        var actSchedulerName = scheduler.getSchedulerName();
        assertThat(actSchedulerName).isEqualTo(expSchedulerName);
    }

}
