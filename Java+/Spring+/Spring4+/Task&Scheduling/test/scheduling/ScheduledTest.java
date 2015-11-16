package scheduling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = ScheduledConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduledTest {
    @Test
    public void name() throws InterruptedException {
        Thread.sleep(5000);
    }
}