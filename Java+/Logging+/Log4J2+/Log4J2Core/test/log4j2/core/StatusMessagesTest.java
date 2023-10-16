package log4j2.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.status.StatusLogger;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Log troubleshooting information about Log4J2.<br/>
 * <a href="https://logging.apache.org/log4j/2.x/manual/configuration.html#StatusMessages">Documentation</a>
 */
class StatusMessagesTest extends BaseLog4jTest {
    @Test
    void logging() {
        System.setProperty("log4j2.debug", "true");
        var log = LogManager.getLogger(getClass());
        log.error("abc");
        var statusDataList = StatusLogger.getLogger().getStatusData();
        assertThat(statusDataList).isNotEmpty();
    }
}
