package catch_exception_in_single_line;

import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;

/**
 * Assert exception with {@link org.junit.Assert#assertThrows(String, Class, ThrowingRunnable)}.
 *
 * @since JUnit 4.13
 */
public class AssertException {
    @Test
    public void assertThrowsTest() {
        String message = "my exception";
        assertThrows(message, IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException(message);
        });
    }
}
