package rule;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.model.TestTimedOutException;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Проверка того, что исключение выбрасывается с помощью
 * правила (Rule) org.junit.rules.ExpectedException.
 */
public class ExpectedExceptionRule {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test() {
        thrown.expect(RuntimeException.class);
        throw new RuntimeException();
    }

    @Test(timeout = 100)
    public void timeout() throws InterruptedException {
        thrown.expect(TestTimedOutException.class);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    public void message() {
        String message = "bad idea!";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(message);
        throw new RuntimeException(message);
    }

    @Test
    public void cause() {
        Exception cause = new Exception();
        thrown.expect(RuntimeException.class);
        thrown.expectCause(equalTo(cause));
        throw new RuntimeException(cause);
    }
}