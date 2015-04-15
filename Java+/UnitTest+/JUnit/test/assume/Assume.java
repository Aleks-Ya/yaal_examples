package assume;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

/**
 * Отличие assumeThat() и assertThat().
 * org.junit.Assume#assumeThat позволяет динамически игнорировать тест.
 * Если предположение (assume) не выполняется, то тест игнорируется.
 */
public class Assume {

    @Test
    public void testAssumeThat() {
        Integer a = -2;
        assumeThat(a, Matchers.greaterThanOrEqualTo(0));
        fail();
    }

    @Test
    public void testAssumeTrueFalse() {
        Integer a = -2;
        assumeTrue(a > 0);
        assumeFalse(a == -2);
        fail();
    }

    @Test
    public void testAssumeNotNull() {
        int a = 1, b = 2;
        Object c = null;
        assumeNotNull(a, b, c);
        fail();
    }

    /**
     * Тест игнорируется, п.ч. падает исключение.
     */
    @Test
    public void testAssumeNoException() {
        try {
            new FileReader("not_exists_file");
        } catch (IOException e) {
            assumeNoException(e);
        }
        fail();
    }
}
