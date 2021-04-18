package java8.io;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AutoCloseableTest {

    @Test
    public void myAutoClosableClass() throws Exception {
        try (MyAutoCloseable closeable = new MyAutoCloseable()) {
            assertFalse(MyAutoCloseable.isClosed);
        }
        assertTrue(MyAutoCloseable.isClosed);
    }

    private static class MyAutoCloseable implements AutoCloseable {
        static boolean isClosed;

        @Override
        public void close() throws Exception {
            isClosed = true;
        }
    }
}
