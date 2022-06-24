package guice.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.LogManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Show Guice log in console.
 */
class GuiceLogTest {
    private static final String STR = "abc";

    @Test
    void log() throws IOException {
        // Configure java.util.logging
        var configIs = GuiceLogTest.class.getClassLoader()
                .getResourceAsStream("logging/GuiceLogTest.properties");
        LogManager.getLogManager().readConfiguration(configIs);

        // Run Guice
        var injector = Guice.createInjector(new AppModule());
        var str = injector.getInstance(String.class);
        assertThat(str).isEqualTo(STR);
    }

    private static class AppModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(String.class).toInstance(STR);
        }
    }
}