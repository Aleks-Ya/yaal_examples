package logback.appender.rollingfile;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Конфигурирование и вывод в RollingFileAppender.
 */
@Disabled("Run individually")
class RollingFileAppenderTest {
    private static final Logger log;

    static {
        System.setProperty("logback.configurationFile", "logback/appender/rollingfile/logback.xml");
        log = LoggerFactory.getLogger(RollingFileAppenderTest.class);
    }

    /**
     * Насильно вызывется метод RollingFileAppender#rollover(),
     * чтобы начать писать в следующий файл.
     * Не работает, т.к. непонятно, как получить доступ к аппендерам родительских логгеров.
     */
    private static void rollover() {
        var log1 = (ch.qos.logback.classic.Logger) log;
        var appenders = log1.iteratorForAppenders();
        while (appenders.hasNext()) {
            var appender = appenders.next();
            if (appender instanceof RollingFileAppender<ILoggingEvent> rfAppender) {
                rfAppender.rollover();
            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Test
    void test() throws InterruptedException {
        log.info("Hi!");
        rollover();
        log.info("Buy!");
        while (true) {
            log.debug(new Date().toString());
            TimeUnit.MICROSECONDS.sleep(100);
        }
    }
}
