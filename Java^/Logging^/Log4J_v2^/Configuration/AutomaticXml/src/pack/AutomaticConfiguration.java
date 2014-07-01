package pack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Документация: http://logging.apache.org/log4j/2.x/manual/configuration.html#AutomaticConfiguration
 */
public class AutomaticConfiguration {

    public static void main(String[] args) {
        Logger root = LogManager.getRootLogger();
        printLogger(root);

        Logger currClass = LogManager.getLogger();
        printLogger(currClass);
    }

    private static void printLogger(Logger log) {
        log.trace("Name={} Level={}", log.getName(), log.getLevel());
        log.error("Name={} Level={}", log.getName(), log.getLevel());
    }

}