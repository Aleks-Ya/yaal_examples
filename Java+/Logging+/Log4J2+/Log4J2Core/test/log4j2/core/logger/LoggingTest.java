package log4j2.core.logger;

import log4j2.core.BaseLog4jTest;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Log a message.
 */
class LoggingTest extends BaseLog4jTest {
    @Test
    void logging() {
        try (var out = redirectOutput()) {
            var log = LogManager.getLogger(getClass());
            log.error("abc");
            assertThat(out.toString()).contains("abc");
        }
    }
}
