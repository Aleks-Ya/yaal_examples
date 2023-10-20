package jul.logger;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static jul.Helper.loadConfig;
import static org.assertj.core.api.Assertions.assertThat;

public class LogLevelTest {

    @Test
    void defaultLogLevel() {
        loadConfig("jul/logger/LogLevelTest.properties");

        var rootLog = Logger.getLogger("");
        assertThat(rootLog.getLevel()).isEqualTo(Level.SEVERE);

        var log = Logger.getLogger("any.logger");
        assertThat(log.getLevel()).isNull();//Level is inherited from parent
        var parent = log.getParent();
        assertThat(parent).isEqualTo(rootLog);
    }

    @Test
    void specificLoggerLevel() {
        loadConfig("jul/logger/LogLevelTest.properties");

        var log = Logger.getLogger("a.b.c");
        assertThat(log.getLevel()).isEqualTo(Level.FINEST);
    }

    @Test
    void setLogLevelViaSystemProperties() {
        System.setProperty("java.util.logging.my.logger.level", "FINEST");
        System.setProperty("my.logger.level", "FINEST");
        System.setProperty("java.util.logging.ConsoleHandler.level", "FINEST");
        var log = Logger.getLogger("my.logger");
        log.warning("WARN!!!!!!!!!!!");
        log.finest("FINEST!!!!!!!!!!!");
        assertThat(log.getLevel()).isNull();//DOES NOT WORK
    }

}
