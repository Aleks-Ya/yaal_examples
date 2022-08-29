package quartz.config.custom.application_yaml;

import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Configure Quartz in application.yaml
 */
@SpringBootTest(webEnvironment = NONE, classes = QuartzConfig.class, properties = {
        "spring.config.location=classpath:config/custom/application_yaml/application.yaml",
        "org.quartz.properties=absent.properties"})
class QuartzConfigInApplicationYamlTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    void test() throws SchedulerException {
        var name = scheduler.getSchedulerName();
        assertThat(name).isEqualTo("instance-application-yaml");
    }
}