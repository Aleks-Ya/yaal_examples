package micrometer;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Use @{@link Timed} on methods.
 */
@Service
class TimedMethodService {
    private static final Random random = new Random();

    @Timed(value = "execution_time_method", description = "Execution time of a method")
    void doSomeWork() throws InterruptedException {
        var millis = random.nextInt(500, 5000);
        Thread.sleep(millis);
    }
}
