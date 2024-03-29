package jul.manager;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ConfigFromPropertiesFileTest {
    private static final Logger julLogger = Logger.getLogger(ConfigFromPropertiesFileTest.class.getName());

    @Test
    void viaSystemProperty() throws IOException {
        var configUrl = ConfigFromPropertiesFileTest.class.getClassLoader()
                .getResource("jul/manager/ConfigFromPropertiesFileTest.properties");
        var configPath = Objects.requireNonNull(configUrl).getFile();
        System.setProperty("java.util.logging.config.file", configPath);
        LogManager.getLogManager().readConfiguration();

        julLogger.warning("Test LogLevelTest.properties (system property)");
    }

    @Test
    void viaInputStream() throws IOException {
        var configIs = ConfigFromPropertiesFileTest.class.getClassLoader()
                .getResourceAsStream("jul/manager/ConfigFromPropertiesFileTest.properties");
        LogManager.getLogManager().readConfiguration(configIs);

        julLogger.warning("Test LogLevelTest.properties (InputStream)");
    }
}
