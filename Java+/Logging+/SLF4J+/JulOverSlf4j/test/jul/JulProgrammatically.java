package jul;

import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Logger;

/**
 * Setup jul-over-slf4j programmatically.
 */
public class JulProgrammatically {
    private static final Logger julLogger = Logger.getLogger(JulProgrammatically.class.getName());

    @Test
    public void log() {
        //register SLF4JBridgeHandler as handler for the j.u.l. root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        julLogger.warning("Test SLF4JBridgeHandler");
    }

}
