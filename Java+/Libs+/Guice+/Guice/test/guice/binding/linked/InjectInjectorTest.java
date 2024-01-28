package guice.binding.linked;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

class InjectInjectorTest {

    @Test
    void bind() {
        var injector = Guice.createInjector();
        var user = injector.getInstance(InjectorUser.class);
        assertThat(user.getInjector()).isSameAs(injector);
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