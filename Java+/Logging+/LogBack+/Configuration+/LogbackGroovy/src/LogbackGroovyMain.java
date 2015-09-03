import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuring LogBack with logback.xml
 */
public class LogbackGroovyMain {
    public static void main(String[] args) {
        Logger myLogger = LoggerFactory.getLogger("my.logger");
        myLogger.debug("Hello my world.");

        Logger rootLogger = LoggerFactory.getLogger("~");
        rootLogger.info("Hello root world.");
    }
}