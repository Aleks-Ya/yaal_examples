package slf4j;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

class LogExceptionsTest {
    private static final Logger log = LoggerFactory.getLogger(LogExceptionsTest.class);

    @Test
    void logException() {
        var cause = new IllegalAccessException("Cause message");
        var exception = new IOException("Exception message", cause);
        log.error("Log message", exception);
    }

    @Test
    void logSingleArgumentAndException() {
        var cause = new IllegalAccessException("Cause message");
        var exception = new IOException("Exception message", cause);
        var user = "john";
        log.error("Log message from {} user", user, exception);
    }

    @Test
    void logTwoArgumentsAndException() {
        var cause = new IllegalAccessException("Cause message");
        var exception = new IOException("Exception message", cause);
        var user = "john";
        var server = "server.host.org";
        log.error("Log message from {} user ({})", user, server, exception);
    }

    @Test
    void logThreeArgumentsAndException() {
        var cause = new IllegalAccessException("Cause message");
        var exception = new IOException("Exception message", cause);
        var user = "john";
        var server = "server.host.org";
        var location = "Moscow";
        log.error("Log message from {} user ({} in {})", user, server, location, exception);
    }

    @Test
    void logExceptionInParametrizedMessages() {
        var log = LoggerFactory.getLogger(LogExceptionsTest.class);
        var cause = new NullPointerException("Вложенное исключение");
        var exception = new RuntimeException("Исключение", cause);
        var user = "Алексей";
        var country = "Россия";
        var date = new Date();
        log.error("Брошено исключение", exception);
        log.error("Брошено исключение (пользователь: {})", user, exception);
        log.error("Брошено исключение (пользователь: {}, страна: {})", user, country, exception);
        log.error("Брошено исключение (пользователь: {}, страна: {}, дата: {})",
                user, country, String.format("%tD", date), exception);
    }

}