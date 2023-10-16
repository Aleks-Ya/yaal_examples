package log4j2.properties;

import org.apache.logging.log4j.LogManager;

/**
 * @see LogPropertiesFileConfigTest
 */
@SuppressWarnings("JavadocReference")
public class LogPropertiesFileConfigMain {
    public static void main(String[] args) {
        var log = LogManager.getLogger(LogPropertiesFileConfigMain.class);
        log.info("Hello, Log4J!");
    }
}