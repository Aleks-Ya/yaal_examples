package guice.binding.child_injector;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ChildInjectorTest {
    private static final String STR = "hi";
    private static final Integer INT = 7;

    @Test
    public void bind() {
        var injector = Guice.createInjector(new MainModule());
        var childInjector = injector.createChildInjector(new ChildModule());
        var str = childInjector.getInstance(String.class);
        var integer = childInjector.getInstance(Integer.class);
        assertThat(str, equalTo(STR));
        assertThat(integer, equalTo(INT));
    }

    private static class MainModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Integer.class).toInstance(INT);
        }
    }

    private static class ChildModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(String.class).toInstance(STR);
        }
    }
}