package log4j2.core.configuration.properties;

import log4j2.core.BaseLog4jTest;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.TRACE;
import static org.assertj.core.api.Assertions.assertThat;

class SetLogLevelTest extends BaseLog4jTest {
    @Test
    void setLogLevel() {
        System.setProperty("log4j2.configurationFile", "classpath:log4j2/core/properties/SetLogLevelTest.properties");
        var app1Log = LogManager.getLogger("app1");
        var app1SubExplicitLog = LogManager.getLogger("app1.sub_explicit");
        var app1SubImplicitLog = LogManager.getLogger("app1.sub_implicit");

        assertThat(app1Log.getLevel()).isEqualTo(TRACE);
        assertThat(app1SubExplicitLog.getLevel()).isEqualTo(TRACE);
        assertThat(app1SubImplicitLog.getLevel()).isEqualTo(ERROR);
    }
}
