package log4j2.java;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import util.InputStreamUtil;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @see JavaAutomaticConfigMain
 */
class JavaAutomaticConfigTest {
    @Test
    void logging() {
        LogManager.shutdown();
        try (var out = InputStreamUtil.redirectStdOut()) {
            var log = LogManager.getLogger(JavaAutomaticConfigTest.class);
            var message = "abc";
            log.error(message);
            assertThat(out.toString()).contains("[Test worker] ERROR log4j2.java.JavaAutomaticConfigTest - abc");
        }
    }
}
