package logback.appender.custom;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MyConsoleAppenderTest {
    private static final Logger log;

    static {
        System.setProperty("logback.configurationFile", "logback/appender/custom/logback.xml");
        log = LoggerFactory.getLogger(MyConsoleAppenderTest.class);
    }

    @Test
    void test() {
        log.info("Hi");
    }
}
