package log4j2.core;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use "log4j2.configurationFile" system property to specify configuration file location.
 */
class ConfigurationFilePropertyTest extends BaseLog4jTest {
    @Test
    void file() {
        System.setProperty("log4j2.configurationFile", "classpath:log4j2/core/ConfigurationFilePropertyTest.xml");
        try (var out = redirectOutput()) {
            var log = LogManager.getLogger(getClass());
            log.error("abc");
            assertThat(out.toString()).contains("XML PATTERN - abc");
        }
    }
}
