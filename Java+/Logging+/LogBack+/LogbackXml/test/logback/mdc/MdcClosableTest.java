package logback.mdc;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.assertj.core.api.Assertions.assertThat;

class MdcClosableTest extends BaseLogbackTest {
    @Test
    void mdc() {
        try (var stdOut = reinitialize("logback/mdc/SimpleMdcTest.xml")) {
            var log = LoggerFactory.getLogger(MdcClosableTest.class);
            try (var ignored = MDC.putCloseable("first", "John")) {
                log.info("MDC works 1");
            }
            try (var ignored = MDC.putCloseable("last", "Smith")) {
                log.info("MDC works 2");
            }
            assertThat(stdOut).hasToString("""
                    John  - MDC works 1
                     Smith - MDC works 2
                    """);
        }
    }
}
