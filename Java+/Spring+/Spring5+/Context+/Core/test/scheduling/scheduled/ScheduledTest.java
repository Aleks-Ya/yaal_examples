package scheduling.scheduled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"my-prop = 1000"})
@ContextConfiguration(classes = ScheduledConfig.class)
public class ScheduledTest {
    @Test
    public void name() throws InterruptedException {
        Thread.sleep(5000);
    }
}