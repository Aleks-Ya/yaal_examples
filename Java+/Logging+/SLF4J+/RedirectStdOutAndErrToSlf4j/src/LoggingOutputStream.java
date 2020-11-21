import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Source: https://gist.github.com/martijnvogten/6088904
 */
class LoggingOutputStream extends OutputStream {

    static void redirectSysOutAndSysErr(Logger logger) {
        System.setOut(new PrintStream(new LoggingOutputStream(logger, Level.INFO)));
        System.setErr(new PrintStream(new LoggingOutputStream(logger, Level.ERROR)));
    }

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
    private final Logger logger;
    private final Level level;

    private LoggingOutputStream(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    @Override
    public void write(int b) {
        if (b == '\n') {
            String line = baos.toString();
            baos.reset();

            switch (level) {
                case TRACE:
                    logger.trace(line);
                    break;
                case DEBUG:
                    logger.debug(line);
                    break;
                case ERROR:
                    logger.error(line);
                    break;
                case INFO:
                    logger.info(line);
                    break;
                case WARN:
                    logger.warn(line);
                    break;
            }
        } else {
            baos.write(b);
        }
    }

}