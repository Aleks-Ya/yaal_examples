package log4j2.properties;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import util.InputStreamUtil;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @see LogPropertiesFileConfigMain
 */
class LogPropertiesFileConfigTest {
    @Test
    void logging() {
        LogManager.shutdown();
        try (var out = InputStreamUtil.redirectStdOut()) {
            var log = LogManager.getLogger(LogPropertiesFileConfigTest.class);
            var message = "abc";
            log.error(message);
            assertThat(out.toString()).isEqualTo("DEFAULT PATTERN: abc\n");
        }
    }
}
