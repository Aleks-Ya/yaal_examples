package micrometer;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Use @{@link Timed} on methods.
 */
@Service
@Timed(value = "execution_time_class", description = "Execution time of a class")
class TimedClassService {
    private static final Random random = new Random();

    void doSomeWork1() throws InterruptedException {
        var millis = random.nextInt(500, 2000);
        Thread.sleep(millis);
    }

    void doSomeWork2() throws InterruptedException {
        var millis = random.nextInt(300, 1000);
        Thread.sleep(millis);
    }
}
