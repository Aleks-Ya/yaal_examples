package pack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Документация: http://logging.apache.org/log4j/2.x/manual/configuration.html#AutomaticConfiguration
 */
public class AutomaticConfiguration {
    private static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        LOG.fatal("Hello, World!");
        LOG.fatal("My name is '{}'", LOG.getName());
    }
}