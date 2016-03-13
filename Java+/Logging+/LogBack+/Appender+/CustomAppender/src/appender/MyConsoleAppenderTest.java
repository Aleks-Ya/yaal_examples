package appender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyConsoleAppenderTest {
    private static Logger log = LoggerFactory.getLogger(MyConsoleAppenderTest.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("Hi");
    }}
