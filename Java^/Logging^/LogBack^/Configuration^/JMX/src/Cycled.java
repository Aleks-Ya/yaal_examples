import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Класс работает в цикле предоставляя возможность
 * управлять LogBack через JMX.
 */
public class Cycled {
    private static Logger log = LoggerFactory.getLogger("my");

    public static void main(String[] args) throws InterruptedException {
        long i = 0;
        while(true) {
            log.info(String.valueOf(i));
            i++;
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
