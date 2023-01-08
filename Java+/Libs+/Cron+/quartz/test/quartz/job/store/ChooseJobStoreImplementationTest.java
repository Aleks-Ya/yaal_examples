package quartz.job.store;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.simpl.RAMJobStore;
import quartz.Factory;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.impl.StdSchedulerFactory.PROP_JOB_STORE_CLASS;

class ChooseJobStoreImplementationTest {
    @Test
    void defaultJobStore() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler();
            assertThat(scheduler.getMetaData().getJobStoreClass()).isEqualTo(RAMJobStore.class);
        }
    }

    @Test
    void configureJobStore() throws SchedulerException {
        try (var factory = new Factory()) {
            var scheduler = factory.newScheduler(Map.of(
                    PROP_JOB_STORE_CLASS, RAMJobStore.class.getName(),
                    "org.quartz.threadPool.threadCount", "1"));
            assertThat(scheduler.getMetaData().getJobStoreClass()).isEqualTo(RAMJobStore.class);
        }
    }
}
