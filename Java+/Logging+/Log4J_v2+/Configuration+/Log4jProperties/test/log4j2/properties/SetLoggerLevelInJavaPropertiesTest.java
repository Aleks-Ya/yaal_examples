package log4j2.properties;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * DO NOT WORK
 */
class SetLoggerLevelInJavaPropertiesTest {
    @Test
    void logging() {
        System.setProperty("log4j2.logger.my.log1", "TRACE");
        System.setProperty("log4j.logger.my.log1", "TRACE");
        System.setProperty("logger.my.log1", "TRACE");

        System.setProperty("log4j2.logger.my.log1.level", "TRACE");
        System.setProperty("log4j.logger.my.log1.level", "TRACE");
        System.setProperty("logger.my.log1.level", "TRACE");
        System.setProperty("logging.level", "TRACE");

        LogManager.shutdown();
        assertThat(LogManager.getRootLogger().getLevel()).isEqualTo(DEBUG);
        assertThat(LogManager.getLogger("my.log1").getLevel()).isEqualTo(DEBUG);
    }
}
