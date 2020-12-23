package logger;

import org.slf4j.LoggerFactory;

/**
 * Set default logger level by Java system property.
 * Run: with property "-Dorg.slf4j.simpleLogger.defaultLogLevel=ERROR"
 */
public class SetDefaultLogLevelBySystemProperty {
    public static void main(String[] args) {
        var log = LoggerFactory.getLogger("the.logger");
        log.error("You MUST see this");
        log.info("You MUST NOT see this");
    }
}