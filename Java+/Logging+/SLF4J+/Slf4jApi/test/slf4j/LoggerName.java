package slf4j;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerName {
    private static final Logger log = LoggerFactory.getLogger(LoggerName.class);

    @Test
    void loggerName() {
        assertThat(log.getName()).isEqualTo(slf4j.LoggerName.class.getName());
    }

}