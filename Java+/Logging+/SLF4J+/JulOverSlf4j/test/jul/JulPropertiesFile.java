package jul;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Setup jul-over-slf4j with a property file (default is $JAVA_HOME/conf/logging.properties).
 */
public class JulPropertiesFile {
    private static final Logger julLogger = Logger.getLogger(JulPropertiesFile.class.getName());

    @Test
    public void viaSystemProperty() throws IOException {
        URL configUrl = JulPropertiesFile.class.getClassLoader().getResource("logging.properties");
        String configPath = Objects.requireNonNull(configUrl).getFile();
        System.setProperty("java.util.logging.config.file", configPath);
        LogManager.getLogManager().readConfiguration();

        julLogger.warning("Test logging.properties (system property)");
    }

    @Test
    public void viaInputStream() throws IOException {
        InputStream configIs = JulPropertiesFile.class.getClassLoader().getResourceAsStream("logging.properties");
        LogManager.getLogManager().readConfiguration(configIs);

        julLogger.warning("Test logging.properties (InputStream)");
    }
}
