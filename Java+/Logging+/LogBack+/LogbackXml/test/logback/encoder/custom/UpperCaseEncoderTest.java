package logback.encoder.custom;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class UpperCaseEncoderTest extends BaseLogbackTest {
    @Test
    void test() {
        try (var stdOut = reinitialize("logback/encoder/custom/logback.xml")) {
            var log = LoggerFactory.getLogger(UpperCaseEncoderTest.class);
            log.info("hi");
            assertThat(stdOut).hasToString("UPPER CASE ENCODER: HI");
        }
    }
}
