package logback.mdc;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MdcClosableManuallyTest extends BaseLogbackTest {
    @Test
    void mdc() {
        try (var stdOut = reinitialize("logback/mdc/SimpleMdcTest.xml")) {
            var log = LoggerFactory.getLogger(MdcClosableManuallyTest.class);
            try (var ignored = new MdcAutoClosable(Map.of("first", "John"))) {
                log.info("MDC works 1");
            }
            try (var ignored = new MdcAutoClosable(Map.of("last", "Smith"))) {
                log.info("MDC works 2");
            }
            assertThat(stdOut).hasToString("""
                    John  - MDC works 1
                     Smith - MDC works 2
                    """);
        }
    }

    static class MdcAutoClosable implements AutoCloseable {
        public MdcAutoClosable(Map<String, String> map) {
            map.forEach(MDC::put);
        }

        @Override
        public void close() {
            MDC.clear();
        }
    }
}
