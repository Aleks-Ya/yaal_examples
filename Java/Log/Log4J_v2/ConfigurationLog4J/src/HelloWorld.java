import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloWorld {
    private static Logger logger = LogManager.getLogger("HelloWorld");
    public static void main(String[] args) {
        logger.fatal("Hello, World!");
    }
}