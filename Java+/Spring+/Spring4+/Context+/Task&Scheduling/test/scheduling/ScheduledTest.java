package scheduling;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"my-prop = 1000"})
@ContextConfiguration(classes = ScheduledConfig.class)
class ScheduledTest {
    @Test
    void name() throws InterruptedException {
        Thread.sleep(5000);
    }
}