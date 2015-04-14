package rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Проверка того, что исключение выбрасывается с помощью
 * правила (Rule) org.junit.rules.ExpectedException.
 */
public class ExpectedExceptionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test() {
        thrown.expect(RuntimeException.class);
        throw new RuntimeException();
    }
}