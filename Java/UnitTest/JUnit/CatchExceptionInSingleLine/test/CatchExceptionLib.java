import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.catchexception.CatchException.*;


/**
 * Библиотека CatchException.
 * https://code.google.com/p/catch-exception/
 */
public class CatchExceptionLib {
    @Test
    public void test() {
        List myList = new ArrayList();
        catchException(myList).get(0);
        assert caughtException() instanceof IndexOutOfBoundsException;
    }
}
