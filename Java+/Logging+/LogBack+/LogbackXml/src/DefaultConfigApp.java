import org.slf4j.LoggerFactory;

/**
 * Configuring LogBack with logback.xml
 */
public class DefaultConfigApp {
    public static void main(String[] args) {
        var myLogger = LoggerFactory.getLogger("my.logger");
        myLogger.debug("Hello my world.");

        var rootLogger = LoggerFactory.getLogger("~");
        rootLogger.info("Hello root world.");
    }
}