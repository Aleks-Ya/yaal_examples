package log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

class PropertyConfiguratorTest {

    @Test
    void config() {
        var logger = Logger.getLogger(PropertyConfiguratorTest.class);
        var resource = ResourceUtil.resourceToFile(getClass(), "log4j_PropertyConfiguratorTest.properties").getAbsolutePath();
        PropertyConfigurator.configure(resource);
        logger.info("This is my first log4j's statement");
    }

}