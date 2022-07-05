package scheduling;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Использование TaskScheduler для запуска задач по расписанию.
 */
@ContextConfiguration(classes = TaskSchedulerConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
class TaskSchedulerConfigTest {
    @Test
    void test() throws InterruptedException {
        Thread.sleep(5000);
    }
}