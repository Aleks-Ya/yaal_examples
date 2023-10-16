package log4j2.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Test;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.ERROR;
import static org.assertj.core.api.Assertions.assertThat;

class SetLogLevelAtRuntimeTest extends BaseLog4jTest {
    @Test
    void logging() {
        var appLog = LogManager.getLogger("app");
        var subLog1 = LogManager.getLogger("app.logger1");
        var subLog2 = LogManager.getLogger("app.logger2");

        assertThat(subLog1.getLevel()).isEqualTo(ERROR);
        assertThat(subLog2.getLevel()).isEqualTo(ERROR);
        assertThat(appLog.getLevel()).isEqualTo(ERROR);

        Configurator.setLevel(appLog, DEBUG);

        assertThat(appLog.getLevel()).isEqualTo(DEBUG);
        assertThat(subLog1.getLevel()).isEqualTo(DEBUG);
        assertThat(subLog2.getLevel()).isEqualTo(DEBUG);
    }
}
