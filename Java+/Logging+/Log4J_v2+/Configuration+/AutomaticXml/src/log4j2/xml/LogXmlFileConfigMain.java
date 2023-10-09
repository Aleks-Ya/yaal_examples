package log4j2.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @see LogXmlFileConfigTest
 */
@SuppressWarnings("JavadocReference")
public class LogXmlFileConfigMain {

    public static void main(String[] args) {
        var root = LogManager.getRootLogger();
        printLogger(root);

        var currClass = LogManager.getLogger();
        printLogger(currClass);
    }

    private static void printLogger(Logger log) {
        log.trace("Name={} Level={}", log.getName(), log.getLevel());
        log.error("Name={} Level={}", log.getName(), log.getLevel());
    }

}