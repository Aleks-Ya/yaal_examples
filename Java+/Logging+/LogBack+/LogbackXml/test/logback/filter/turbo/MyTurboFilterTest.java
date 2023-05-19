package logback.filter.turbo;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class MyTurboFilterTest extends BaseLogbackTest {
    @Test
    void test() {
        try (var stdOut = reinitialize("logback/filter/turbo/logback.xml")) {
            var log = LoggerFactory.getLogger(MyTurboFilterTest.class);
            log.info(MyTurboFilter.ACCEPT_MARKER, "Hi");
            assertThat(stdOut).hasToString("INFO  logback.filter.turbo.MyTurboFilterTest Hi");
        }
    }
}
