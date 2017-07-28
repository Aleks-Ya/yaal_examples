import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 * Redirect StdOut and StdErr to Slf4j's logger.
 */
public class RedirectStdoutToSlf4j {
    private static final Logger log = LoggerFactory.getLogger(RedirectStdoutToSlf4j.class);

    public static void main(String[] args) {
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();

        log.info("Test log");
        System.out.println("Message to stdout");
        System.out.println("Message to stderr");
    }
}
