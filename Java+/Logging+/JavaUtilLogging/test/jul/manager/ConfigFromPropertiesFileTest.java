package jul.manager;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ConfigFromPropertiesFileTest {
    private static final Logger julLogger = Logger.getLogger(ConfigFromPropertiesFileTest.class.getName());

    @Test
    public void viaSystemProperty() throws IOException {
        URL configUrl = ConfigFromPropertiesFileTest.class.getClassLoader()
                .getResource("jul/manager/ConfigFromPropertiesFileTest.properties");
        String configPath = Objects.requireNonNull(configUrl).getFile();
        System.setProperty("java.util.logging.config.file", configPath);
        LogManager.getLogManager().readConfiguration();

        julLogger.warning("Test LogLevelTest.properties (system property)");
    }

    @Test
    public void viaInputStream() throws IOException {
        InputStream configIs = ConfigFromPropertiesFileTest.class.getClassLoader()
                .getResourceAsStream("jul/manager/ConfigFromPropertiesFileTest.properties");
        LogManager.getLogManager().readConfiguration(configIs);

        julLogger.warning("Test LogLevelTest.properties (InputStream)");
    }
}
