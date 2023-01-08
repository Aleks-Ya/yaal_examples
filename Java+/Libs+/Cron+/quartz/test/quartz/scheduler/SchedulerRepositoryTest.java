package quartz.scheduler;

import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link org.quartz.impl.SchedulerRepository} holds unique instances of {@link Scheduler}s.
 */
class SchedulerRepositoryTest {
    @Test
    void getDefaultScheduler() throws SchedulerException {
        var scheduler1 = StdSchedulerFactory.getDefaultScheduler();
        var scheduler2 = StdSchedulerFactory.getDefaultScheduler();
        assertThat(scheduler1).isEqualTo(scheduler2);
        scheduler1.shutdown(false);
    }
}
