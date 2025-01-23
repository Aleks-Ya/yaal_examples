package log4j2.core.logger;

import log4j2.core.BaseLog4jTest;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetLoggerTest extends BaseLog4jTest {
    @Test
    void getDefaultLogger() {
        var logDefault = LogManager.getLogger();
        var logExplicitClass = LogManager.getLogger(getClass());
        var logExplicitName = LogManager.getLogger(getClass().getName());
        assertThat(logDefault).isSameAs(logExplicitName);
        assertThat(logDefault).isSameAs(logExplicitClass);
    }
}
