package logback.appender.custom;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class MyConsoleAppenderTest extends BaseLogbackTest {
    @Test
    void test() {
        try (var stdOut = reinitialize("logback/appender/custom/logback.xml")) {
            var log = LoggerFactory.getLogger(MyConsoleAppenderTest.class);
            log.info("Hi");
            assertThat(stdOut).hasToString("PREFIX_MyConsoleAppender: Hi\n");
        }
    }
}
