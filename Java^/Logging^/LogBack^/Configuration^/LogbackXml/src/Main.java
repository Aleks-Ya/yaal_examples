import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuring LogBack with logback.xml
 */
public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("my.logger");
        logger.debug("Hello world.");
    }
}