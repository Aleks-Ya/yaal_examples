package logback.appender.console;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleAppenderTest extends BaseLogbackTest {
    @Test
    void test() {
        try (var stdOut = reinitialize("logback/appender/console/logback.xml")) {
            var log = LoggerFactory.getLogger(ConsoleAppenderTest.class);
            log.info("Hi");
            assertThat(stdOut).hasToString("Console: Hi");
        }
    }
}
