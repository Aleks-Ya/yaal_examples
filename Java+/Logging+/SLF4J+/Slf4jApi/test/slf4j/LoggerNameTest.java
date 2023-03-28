package slf4j;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerNameTest {
    private static final Logger log = LoggerFactory.getLogger(LoggerNameTest.class);

    @Test
    void loggerName() {
        assertThat(log.getName()).isEqualTo(LoggerNameTest.class.getName());
    }

}