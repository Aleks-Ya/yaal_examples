package appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * @author Yablokov Aleksey
 */
public class MyConsoleAppender extends AppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent event) {
        System.out.println("MyConsoleAppender:" + event.getFormattedMessage());
    }
}
