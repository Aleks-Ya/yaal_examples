package guice.binding.provides;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProvidesBindingTest {
    private static final String expected = "hello world";

    @Test
    public void bind() {
        var injector = Guice.createInjector(new DemoModule());
        var actual = injector.getInstance(String.class);
        assertThat(actual, equalTo(expected));
    }

    private static class DemoModule extends AbstractModule {
        @Provides
        static String provideString() {
            return expected;
        }
    }
}