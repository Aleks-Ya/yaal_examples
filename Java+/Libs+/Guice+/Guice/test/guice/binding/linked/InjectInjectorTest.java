package guice.binding.linked;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InjectInjectorTest {

    @Test
    public void bind() {
        var injector = Guice.createInjector();
        var user = injector.getInstance(InjectorUser.class);
        assertThat(user.getInjector(), is(injector));
    }

    private static class InjectorUser {
        private final Injector injector;

        @Inject
        public InjectorUser(Injector injector) {
            this.injector = injector;
        }

        public Injector getInjector() {
            return injector;
        }
    }
}