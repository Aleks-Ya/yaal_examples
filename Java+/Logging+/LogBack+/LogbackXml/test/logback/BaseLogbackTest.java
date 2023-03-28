package logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public abstract class BaseLogbackTest {
    public ByteArrayOutputStream reinitialize(String resource) {
        try (var is = BaseLogbackTest.class.getClassLoader().getResourceAsStream(resource)) {
            var loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.reset();
            var configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            configurator.doConfigure(is);
            return redirectStdOut();
        } catch (JoranException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ByteArrayOutputStream redirectStdOut() {
        var byteOS = new ByteArrayOutputStream();
        var printStream = new PrintStream(byteOS);
        System.setOut(printStream);
        return byteOS;
    }
}
