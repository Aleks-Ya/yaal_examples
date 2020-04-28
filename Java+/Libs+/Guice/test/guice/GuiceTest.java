package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GuiceTest {
    private static final String expected = "hello world";

    public static class DemoModule extends AbstractModule {
        protected void configure() {
            bind(String.class).toInstance(expected);
        }

        @Provides
        static Integer provideCount() {
            return 3;
        }
    }

    @Test
    public void bind() {
        Injector injector = Guice.createInjector(new DemoModule());
        String actual = injector.getInstance(String.class);
        assertThat(actual, equalTo(expected));
    }
}