package logback.mdc;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example from <a href="https://logback.qos.ch/manual/mdc.html">manual</a>.
 */
class SimpleMdcTest extends BaseLogbackTest {
    @Test
    void mdc() {
        var stdOut = reinitialize("logback/mdc/SimpleMdcTest.xml");

        // You can put values in the MDC at any time. Before anything else we put the first name
        MDC.put("first", "Dorothy");

        var log = LoggerFactory.getLogger(SimpleMdcTest.class);
        // We now put the last name
        MDC.put("last", "Parker");

        // The most beautiful two words in the English language according to Dorothy Parker:
        log.info("Check enclosed.");
        log.debug("The most beautiful two words in English.");

        MDC.put("first", "Richard");
        MDC.put("last", "Nixon");
        log.info("I am not a crook.");
        log.info("Attributed to the former US president. 17 Nov 1973.");

        assertThat(stdOut).hasToString("""
                Dorothy Parker - Check enclosed.
                Dorothy Parker - The most beautiful two words in English.
                Richard Nixon - I am not a crook.
                Richard Nixon - Attributed to the former US president. 17 Nov 1973.
                """);
    }
}
