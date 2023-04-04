package simplelogger;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import util.InputStreamUtil;

import static org.assertj.core.api.Assertions.assertThat;

class SetDefaultLogLevelBySystemPropertyTest {
    @Test
    void test() {
        var out = InputStreamUtil.redirectStdErr();
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "ERROR");
        var log = LoggerFactory.getLogger("the.logger");
        log.error("You MUST see this");
        log.info("You MUST NOT see this");
        assertThat(out.toString()).contains("You MUST see this").doesNotContain("You MUST NOT see this");
    }
}
