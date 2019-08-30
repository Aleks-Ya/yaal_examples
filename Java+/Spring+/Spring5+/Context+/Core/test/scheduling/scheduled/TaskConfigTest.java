package scheduling.scheduled;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Не дописан.
 * Использование Task для запуска задач по расписанию.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TaskConfig.class)
public class TaskConfigTest {
    @Test
    public void test() throws InterruptedException {
        Thread.sleep(5000);
    }
}