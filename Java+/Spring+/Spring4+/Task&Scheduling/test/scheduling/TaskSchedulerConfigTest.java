package scheduling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Использование TaskScheduler для запуска задач по расписанию.
 */
@ContextConfiguration(classes = TaskSchedulerConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskSchedulerConfigTest {
    @Test
    public void test() throws InterruptedException {
        Thread.sleep(5000);
    }
}