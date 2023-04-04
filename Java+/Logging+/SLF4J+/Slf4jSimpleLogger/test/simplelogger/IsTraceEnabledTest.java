package simplelogger;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class IsTraceEnabledTest {
    @Test
    void test() {
        System.setProperty("org.slf4j.simpleLogger.log.the.logger1", "ERROR");
        System.setProperty("org.slf4j.simpleLogger.log.the.logger2", "TRACE");
        var errorLogger = LoggerFactory.getLogger("the.logger1");
        assertThat(errorLogger.isTraceEnabled()).isFalse();
        var traceLogger = LoggerFactory.getLogger("the.logger2");
        assertThat(traceLogger.isTraceEnabled()).isTrue();
    }
}
