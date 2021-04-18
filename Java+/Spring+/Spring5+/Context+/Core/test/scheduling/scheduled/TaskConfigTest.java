package scheduling.scheduled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Не дописан.
 * Использование Task для запуска задач по расписанию.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TaskConfig.class)
public class TaskConfigTest {
    @Test
    public void test() throws InterruptedException {
        Thread.sleep(5000);
    }
}