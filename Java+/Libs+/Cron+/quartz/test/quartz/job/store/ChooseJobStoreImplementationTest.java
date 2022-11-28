package quartz.job.store;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.RAMJobStore;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.quartz.impl.StdSchedulerFactory.PROP_JOB_STORE_CLASS;

class ChooseJobStoreImplementationTest {
    @Test
    void defaultJobStore() throws SchedulerException {
        var scheduler = StdSchedulerFactory.getDefaultScheduler();
        assertThat(scheduler.getMetaData().getJobStoreClass()).isEqualTo(RAMJobStore.class);
    }

    @Test
    void configureJobStore() throws SchedulerException {
        var schedulerProperties = new Properties();
        schedulerProperties.setProperty(PROP_JOB_STORE_CLASS, RAMJobStore.class.getName());
        schedulerProperties.setProperty("org.quartz.threadPool.threadCount", "1");
        var factory = new StdSchedulerFactory(schedulerProperties);
        var scheduler = factory.getScheduler();
        assertThat(scheduler.getMetaData().getJobStoreClass()).isEqualTo(RAMJobStore.class);
    }
}
