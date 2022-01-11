package scheduling.scheduled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Использование TaskScheduler для запуска задач по расписанию.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TaskSchedulerConfig.class)
class TaskSchedulerConfigTest {
    @Test
    void test() throws InterruptedException {
        Thread.sleep(5000);
    }
}