package log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class BasicConfig {

    @Test
    public void config() {
        Logger logger = Logger.getLogger(BasicConfig.class);
        BasicConfigurator.configure();
        logger.info("This is my first log4j's statement");
    }

}