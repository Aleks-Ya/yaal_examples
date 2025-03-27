package io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutoCloseableTest {

    @Test
    void myAutoClosableClass() throws Exception {
        try (MyAutoCloseable closeable = new MyAutoCloseable()) {
            assertThat(MyAutoCloseable.isClosed).isFalse();
        }
        assertThat(MyAutoCloseable.isClosed).isTrue();
    }

    private static class MyAutoCloseable implements AutoCloseable {
        static boolean isClosed;

        @Override
        public void close() throws Exception {
            isClosed = true;
        }
    }
}
