package log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

class BasicConfiguratorTest {

    @Test
    void config() {
        var logger = Logger.getLogger(BasicConfiguratorTest.class);
        BasicConfigurator.configure();
        logger.info("This is my first log4j's statement");
    }

}