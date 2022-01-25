package slf4j;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class LogExceptions {
    private static final Logger log = LoggerFactory.getLogger(LogExceptions.class);

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

}