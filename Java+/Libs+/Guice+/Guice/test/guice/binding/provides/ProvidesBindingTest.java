package guice.binding.provides;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Provides;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProvidesBindingTest {
    private static final String expected = "hello world";

    @Test
    void bind() {
        var injector = Guice.createInjector(new DemoModule());
        var actual = injector.getInstance(String.class);
        assertThat(actual).isEqualTo(expected);
    }

    private static class DemoModule extends AbstractModule {
        @Provides
        static String provideString() {
            return expected;
        }
    }
}