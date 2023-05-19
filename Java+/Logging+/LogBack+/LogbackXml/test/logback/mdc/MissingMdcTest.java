package logback.mdc;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Formatting absent MDC keys.
 */
class MissingMdcTest extends BaseLogbackTest {
    @Test
    void mdc() {
        try (var stdOut = reinitialize("logback/mdc/MissingMdcTest.xml")) {
            var log = LoggerFactory.getLogger(MissingMdcTest.class);
            MDC.put("first", "John");
            MDC.put("last", "Dow");
            log.info("Client info is specified.");
            MDC.clear();
            log.info("No client info specified.");
            assertThat(stdOut).hasToString("""
                    John Dow - Client info is specified.
                      - No client info specified.
                    """);
        }
    }
}
