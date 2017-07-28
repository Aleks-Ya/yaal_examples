import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Redirect StdOut and StdErr to Slf4j's logger.
 */
public class RedirectStdoutToSlf4j {
    private static final Logger log = LoggerFactory.getLogger(RedirectStdoutToSlf4j.class);

    public static void main(String[] args) {
        LoggingOutputStream.redirectSysOutAndSysErr(log);

        log.info("Test log");
        System.out.println("Message to stdout");
        System.out.println("Message to stderr");
    }
}
