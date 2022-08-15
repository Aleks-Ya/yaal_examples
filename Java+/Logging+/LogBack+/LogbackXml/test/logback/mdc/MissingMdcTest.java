package logback.mdc;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Formatting absent MDC keys.
 */
class MissingMdcTest {
    @Test
    void mdc() {
        System.setProperty("logback.configurationFile", "logback/mdc/MissingMdcTest.xml");
        var logger = LoggerFactory.getLogger(MissingMdcTest.class);
        MDC.put("first", "John");
        MDC.put("last", "Dow");
        logger.info("Client info is specified.");
        MDC.clear();
        logger.info("No client info specified.");
    }
}
