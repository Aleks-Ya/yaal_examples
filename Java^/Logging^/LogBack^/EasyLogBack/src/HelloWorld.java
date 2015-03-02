import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HelloWorld with LogBack.
 */
public class HelloWorld {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");
        logger.debug("Hello world.");
    }
}
