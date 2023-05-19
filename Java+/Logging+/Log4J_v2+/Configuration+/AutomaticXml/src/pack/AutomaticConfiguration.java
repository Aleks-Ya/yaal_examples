package pack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutomaticConfiguration {

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