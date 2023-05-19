package log4j.core;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import util.InputStreamUtil;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Log a message.
 */
class LoggingTest extends BaseLog4jTest {
    @Test
    void logging() {
        try (var out = redirectOutput()) {
            var log = LogManager.getLogger(LoggingTest.class);
            var message = "abc";
            log.error(message);
            assertThat(out.toString()).contains(message);
        }
    }
}
