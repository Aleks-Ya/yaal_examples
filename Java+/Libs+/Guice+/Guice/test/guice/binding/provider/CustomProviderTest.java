package guice.binding.provider;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class CustomProviderTest {

    @Test
    void bind() {
        var injector = Guice.createInjector(new DemoModule());
        var format = injector.getInstance(NumberFormat.class);
        var numberStr = format.format(1234);
        assertThat(numberStr).isEqualTo("1,234");
    }

    private static class DemoModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Locale.class).toInstance(Locale.ENGLISH);
            bind(NumberFormat.class).toProvider(FormatterProvider.class);
        }
    }

    static class FormatterProvider implements Provider<NumberFormat> {
        private final Locale locale;

        @Inject
        FormatterProvider(Locale locale) {
            this.locale = locale;
        }

        @Override
        public NumberFormat get() {
            return NumberFormat.getIntegerInstance(locale);
        }
    }
}