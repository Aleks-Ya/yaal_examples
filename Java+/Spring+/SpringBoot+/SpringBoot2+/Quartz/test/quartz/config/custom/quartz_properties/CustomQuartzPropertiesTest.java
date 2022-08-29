package quartz.config.custom.quartz_properties;

import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Custom location of quartz.properties.
 */
@SpringBootTest(webEnvironment = NONE, classes = QuartzConfig.class)
class CustomQuartzPropertiesTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    void test() throws SchedulerException {
        var name = scheduler.getSchedulerName();
        assertThat(name).isEqualTo("scheduler-quartz-properties");
    }
}