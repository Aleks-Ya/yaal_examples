package guice.binding.private_module;

import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrivateModuleTest {
    private static final Integer INT = 7;
    private static final Double DOUBLE = 1.1;

    @Test
    void bind() {
        var injector = Guice.createInjector(new HiddenModule());

        //Not exposed binding
        var intBinding = injector.getExistingBinding(Key.get(Integer.class));
        assertThat(intBinding).isNull();

        //Exposed binding
        var doubleValue = injector.getInstance(Double.class);
        assertThat(doubleValue).isEqualTo(DOUBLE);
    }

    private static class HiddenModule extends PrivateModule {
        @Override
        protected void configure() {
            bind(Integer.class).toInstance(INT);
            bind(Double.class).toInstance(DOUBLE);
            expose(Double.class);
        }
    }
}