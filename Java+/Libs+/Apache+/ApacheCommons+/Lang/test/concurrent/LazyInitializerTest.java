package concurrent;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LazyInitializerTest {
    private static final String EXP_VALUE = "abc";
    private static boolean initialized = false;
    private static final LazyInitializer<String> str = new LazyInitializer<>() {
        @Override
        protected String initialize() {
            initialized = true;
            return EXP_VALUE;
        }
    };

    @Test
    void lazy() throws ConcurrentException {
        assertFalse(initialized);
        var actValue = str.get();
        assertThat(actValue, equalTo(EXP_VALUE));
        assertTrue(initialized);
    }
}