package memory.gc;

import java.util.Map;

/**
 * Use "-XX:+PrintFlagsFinal" JVM option.
 */
public class PrintFlagsFinalTest {
    public final String key;

    public PrintFlagsFinalTest(String key) {
        this.key = key;
    }

    public static void main(String args[]) {
        System.setProperty("verbose:gc", "true");
        try {
            Map map = System.getProperties();

            for (; ; ) {
                map.put(new PrintFlagsFinalTest("key"), "value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
