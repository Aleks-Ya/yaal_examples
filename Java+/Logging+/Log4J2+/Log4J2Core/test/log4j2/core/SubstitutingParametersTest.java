package log4j2.core;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubstitutingParametersTest extends BaseLog4jTest {
    @Test
    void parameters() {
        try (var out = redirectOutput()) {
            var log = LogManager.getLogger(getClass());
            log.error("Temperature in {} is {} degree", "London", 25);
            assertThat(out.toString()).contains("Temperature in London is 25 degree");
        }
    }
}
