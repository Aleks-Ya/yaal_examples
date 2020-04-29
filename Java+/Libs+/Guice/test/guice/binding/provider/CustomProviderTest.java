package guice.binding.provider;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.junit.Test;

import java.text.NumberFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CustomProviderTest {

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

    @Test
    public void bind() {
        Injector injector = Guice.createInjector(new DemoModule());
        NumberFormat format = injector.getInstance(NumberFormat.class);
        String numberStr = format.format(1234);
        assertThat(numberStr, equalTo("1,234"));
    }
}