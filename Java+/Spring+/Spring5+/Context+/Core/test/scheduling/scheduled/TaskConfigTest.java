package scheduling.scheduled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Not finished.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TaskConfig.class)
class TaskConfigTest {
    @Test
    void test() throws InterruptedException {
        Thread.sleep(5000);
    }
}