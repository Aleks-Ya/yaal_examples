package logger;

import org.slf4j.LoggerFactory;

/**
 * Set logger level by Java system property.
 * Run: with property "-Dorg.slf4j.simpleLogger.log.the.logger=ERROR"
 */
public class SetLogLevelBySystemProperty {
    public static void main(String[] args) {
        var log = LoggerFactory.getLogger("the.logger");
        log.error("You MUST see this");
        log.info("You MUST NOT see this");
    }
}