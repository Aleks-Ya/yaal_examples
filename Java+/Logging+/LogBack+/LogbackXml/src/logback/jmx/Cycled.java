package logback.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Класс работает в цикле предоставляя возможность
 * управлять LogBack через JMX.
 * <p>
 * Run:
 * java -Dcom.sun.management.jmxremote.port=9999 \
 * -Dcom.sun.management.jmxremote.authenticate=false \
 * -Dcom.sun.management.jmxremote.ssl=false \
 * Cycled
 */
public class Cycled {
    private static final Logger log;

    static {
        System.setProperty("logback.configurationFile", "logback/jmx/logback.xml");
        log = LoggerFactory.getLogger("my");
    }

    public static void main(String[] args) throws InterruptedException {
        long i = 0;
        while (true) {
            log.info(String.valueOf(i));
            i++;
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
