package lang.concurrent;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(initialized).isFalse();
        var actValue = str.get();
        assertThat(actValue).isEqualTo(EXP_VALUE);
        assertThat(initialized).isTrue();
    }
}