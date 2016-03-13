package appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;

/**
 * Custom appender with encoder
 */
public class MyWithEncoderAppender extends OutputStreamAppender<ILoggingEvent> {

    @Override
    public void start() {
        setOutputStream(System.out);
        super.start();
    }
}
