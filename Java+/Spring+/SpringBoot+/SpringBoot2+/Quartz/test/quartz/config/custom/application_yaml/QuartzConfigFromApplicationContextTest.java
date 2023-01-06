package quartz.config.custom.application_yaml;

import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Configure Quartz with properties from ApplicationContext.
 * Quartz will use properties overwritten in ApplicationContext.
 */
@SpringBootTest(webEnvironment = NONE, classes = FromApplicationContextConfig.class,
        properties = "spring.config.location=classpath:config/custom/application_yaml/from_application_context.yaml")
class QuartzConfigFromApplicationContextTest {
    @Autowired
    private Scheduler scheduler;

    @Test
    void test() throws SchedulerException {
        assertThat(scheduler.getSchedulerName()).isEqualTo("from-application-context-scheduler");
    }
}