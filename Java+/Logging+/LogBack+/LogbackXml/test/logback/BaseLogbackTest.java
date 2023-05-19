package logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;
import util.InputStreamUtil;

import java.io.IOException;

public abstract class BaseLogbackTest {
    public InputStreamUtil.RedirectedOutput reinitialize(String resource) {
        try (var is = BaseLogbackTest.class.getClassLoader().getResourceAsStream(resource)) {
            var loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.reset();
            var configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            configurator.doConfigure(is);
            return InputStreamUtil.redirectStdOut();
        } catch (JoranException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
