package rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.model.TestTimedOutException;

import java.util.concurrent.TimeUnit;

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
}