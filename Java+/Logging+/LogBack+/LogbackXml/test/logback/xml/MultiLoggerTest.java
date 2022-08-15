package logback.xml;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
import org.junit.jupiter.api.Test;

import static util.ResourceUtil.resourceToUrl;

class MultiLoggerTest {
    @Test
    void multi() throws JoranException {
        var configFile1 = resourceToUrl(MultiLoggerTest.class, "MultiLoggerTest1.xml");
        var configFile2 = resourceToUrl(MultiLoggerTest.class, "MultiLoggerTest2.xml");
        var c1 = new LoggerContext();
        var c2 = new LoggerContext();
        new ContextInitializer(c1).configureByResource(configFile1);
        new ContextInitializer(c2).configureByResource(configFile2);
        var l1 = c1.getLogger("my.logger");
        var l2 = c2.getLogger("my.logger");
        l1.info("From Logger 1");
        l2.info("From Logger 2");
    }
}
