package log4j;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.apache.log4j.Level.DEBUG;
import static org.assertj.core.api.Assertions.assertThat;

class SetLoggerLevelProgrammaticallyTest {
    @Test
    void config() {
        var logger = Logger.getLogger("my.log1");
        assertThat(logger.getLevel()).isNull();
        logger.setLevel(DEBUG);
        assertThat(logger.getLevel()).isEqualTo(DEBUG);
    }
}