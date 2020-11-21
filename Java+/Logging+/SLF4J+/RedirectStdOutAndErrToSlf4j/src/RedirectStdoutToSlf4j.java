import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Redirect StdOut and StdErr to Slf4j's logger.
 */
public class RedirectStdoutToSlf4j {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(RedirectStdoutToSlf4j.class);
        LoggingOutputStream.redirectSysOutAndSysErr(log);

        log.info("Test log");
        System.out.println("Message to stdout");
        System.out.println("Message to stderr");
    }
}
