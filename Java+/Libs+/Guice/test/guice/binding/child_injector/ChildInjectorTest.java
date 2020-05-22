package guice.binding.child_injector;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ChildInjectorTest {
    private static final String STR = "hi";
    private static final Integer INT = 7;

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

    @Test
    public void bind() {
        Injector injector = Guice.createInjector(new MainModule());
        Injector childInjector = injector.createChildInjector(new ChildModule());
        String str = childInjector.getInstance(String.class);
        Integer integer = childInjector.getInstance(Integer.class);
        assertThat(str, equalTo(STR));
        assertThat(integer, equalTo(INT));
    }
}