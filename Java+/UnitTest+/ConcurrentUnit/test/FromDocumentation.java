import net.jodah.concurrentunit.Waiter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Source: https://github.com/jhalterman/concurrentunit
 */
public class FromDocumentation {

    @Test
    public void shouldFail() {
        assertThrows(AssertionError.class, () -> {
            Waiter waiter = new Waiter();
            new Thread(() -> {
                waiter.assertTrue(false);
            }).start();
            waiter.await();
        });
    }
}
