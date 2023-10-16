package log4j2.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaAutomaticConfigMain {
    private static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        LOG.fatal("Hello, World!");
        LOG.fatal("My name is '{}'", LOG.getName());
    }
}