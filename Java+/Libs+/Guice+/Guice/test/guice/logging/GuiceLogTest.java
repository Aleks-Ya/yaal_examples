package guice.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.LogManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Show Guice log in console.
 */
public class GuiceLogTest {
    private static final String STR = "abc";

    @Test
    public void log() throws IOException {
        // Configure java.util.logging
        var configIs = GuiceLogTest.class.getClassLoader()
                .getResourceAsStream("logging/GuiceLogTest.properties");
        LogManager.getLogManager().readConfiguration(configIs);

        // Run Guice
        var injector = Guice.createInjector(new AppModule());
        var str = injector.getInstance(String.class);
        assertThat(str, equalTo(STR));
    }

    private static class AppModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(String.class).toInstance(STR);
        }
    }
}