package logback.xml;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToFile;

class MultiLoggerTest {
    @Test
    void multi() throws JoranException {
        var configFile1 = resourceToFile(MultiLoggerTest.class, "MultiLoggerTest1.xml");
        assertThat(configFile1).exists();
        var c1 = new LoggerContext();
        var configurator1 = new JoranConfigurator();
        configurator1.setContext(c1);
        configurator1.doConfigure(configFile1);
        var log1 = c1.getLogger("my.logger");

        var configFile2 = resourceToFile(MultiLoggerTest.class, "MultiLoggerTest2.xml");
        assertThat(configFile2).exists();
        var c2 = new LoggerContext();
        var configurator2 = new JoranConfigurator();
        configurator2.setContext(c2);
        c2.reset();
        configurator2.doConfigure(configFile2);
        var log2 = c2.getLogger("my.logger");

        log1.info("From Logger 1");
        log2.info("From Logger 2");
    }
}
