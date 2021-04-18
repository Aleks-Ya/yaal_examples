package guice.binding.private_module;

import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class PrivateModuleTest {
    private static final Integer INT = 7;
    private static final Double DOUBLE = 1.1;

    @Test
    public void bind() {
        var injector = Guice.createInjector(new HiddenModule());

        //Not exposed binding
        var intBinding = injector.getExistingBinding(Key.get(Integer.class));
        assertThat(intBinding, nullValue());

        //Exposed binding
        var doubleValue = injector.getInstance(Double.class);
        assertThat(doubleValue, equalTo(DOUBLE));
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