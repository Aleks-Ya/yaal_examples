package log4j2.xml;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import util.InputStreamUtil;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @see LogXmlFileConfigMain
 */
class LogXmlFileConfigTest {
    @Test
    void logging() {
        LogManager.shutdown();
        try (var out = InputStreamUtil.redirectStdOut()) {
            var log = LogManager.getLogger(LogXmlFileConfigTest.class);
            var message = "abc";
            log.error(message);
            assertThat(out.toString()).isEqualTo("FROM XML FILE CONFIG: abc\n");
        }
    }
}
