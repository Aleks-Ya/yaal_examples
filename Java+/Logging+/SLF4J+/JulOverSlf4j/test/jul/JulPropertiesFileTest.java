package jul;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Setup jul-over-slf4j with a property file (default is $JAVA_HOME/conf/logging.properties).
 */
class JulPropertiesFileTest {
    private static final Logger julLogger = Logger.getLogger(JulPropertiesFileTest.class.getName());

    @Test
    void viaSystemProperty() throws IOException {
        var configUrl = JulPropertiesFileTest.class.getClassLoader().getResource("logging.properties");
        var configPath = Objects.requireNonNull(configUrl).getFile();
        System.setProperty("java.util.logging.config.file", configPath);
        LogManager.getLogManager().readConfiguration();

        julLogger.warning("Test logging.properties (system property)");
    }

    @Test
    void viaInputStream() throws IOException {
        var configIs = JulPropertiesFileTest.class.getClassLoader().getResourceAsStream("logging.properties");
        LogManager.getLogManager().readConfiguration(configIs);

        julLogger.warning("Test logging.properties (InputStream)");
    }
}
