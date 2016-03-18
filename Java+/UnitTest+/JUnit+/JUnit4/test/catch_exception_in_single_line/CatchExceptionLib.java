package catch_exception_in_single_line;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.CatchException.verifyException;


/**
 * Проверка выбрасывания исключения в одну строку.
 * Библиотека CatchException.
 * https://code.google.com/p/catch-exception/
 */
public class CatchExceptionLib {
    @Test
    public void catchExc() {
        List myList = new ArrayList();
        catchException(myList).get(0);
        assert caughtException() instanceof IndexOutOfBoundsException;
    }

    @Test
    public void verifyExc() {
        List myList = new ArrayList();
        verifyException(myList, IndexOutOfBoundsException.class).get(0);
    }

    @Test
    public void verifyPackageMethod() {
        PackageMethod obj = new PackageMethod();
        verifyException(obj, IllegalArgumentException.class).testMe();
    }
}

class PackageMethod {
    protected void testMe() {
        throw new IllegalArgumentException();
    }
}
