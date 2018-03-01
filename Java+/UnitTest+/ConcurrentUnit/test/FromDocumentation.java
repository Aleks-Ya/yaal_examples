import net.jodah.concurrentunit.Waiter;
import org.junit.Test;

/**
 * Source: https://github.com/jhalterman/concurrentunit
 */
public class FromDocumentation {

    @Test(expected = AssertionError.class)
    public void shouldFail() throws Throwable {
        final Waiter waiter = new Waiter();

        new Thread(() -> {
            waiter.assertTrue(false);
        }).start();

        waiter.await();
    }
}
