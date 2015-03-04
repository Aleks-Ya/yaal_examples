import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Конфигурирование и вывод в RollingFileAppender.
 */
public class RollingFileAppenderMain {
    private static Logger log = LoggerFactory.getLogger(RollingFileAppenderMain.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("Hi!");
//        rollover();
        log.info("Buy!");
    }

    /**
     * Насильно вызывется метод RollingFileAppender#rollover(),
     * чтобы начать писать в следующий файл.
     * Не работает, т.к. непонятно, как получить доступ к аппендерам родительских логгеров.
     */
    private static void rollover() {
        ch.qos.logback.classic.Logger log1 = (ch.qos.logback.classic.Logger) log;

        Iterator<Appender<ILoggingEvent>> appenders = log1.iteratorForAppenders();

        while (appenders.hasNext()) {
            Appender<ILoggingEvent> appender = appenders.next();
            if (appender instanceof RollingFileAppender) {
                RollingFileAppender<ILoggingEvent> rfAppender = (RollingFileAppender<ILoggingEvent>) appender;
                rfAppender.rollover();
            }
        }
    }
}
