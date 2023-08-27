package jul;

import org.junit.jupiter.api.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Logger;

/**
 * Setup jul-over-slf4j programmatically.
 */
class JulProgrammaticallyTest {
    private static final Logger julLogger = Logger.getLogger(JulProgrammaticallyTest.class.getName());

    @Test
    void log() {
        //register SLF4JBridgeHandler as handler for the j.u.l. root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        julLogger.warning("Test SLF4JBridgeHandler");
    }

}
