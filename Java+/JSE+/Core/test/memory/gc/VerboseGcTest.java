package memory.gc;

import java.util.Map;

/**
 * Use "-verbose:gc" and "-XX:+PrintGCTimeStamps" JVM options.
 */
public class VerboseGcTest {
    public final String key;

    public VerboseGcTest(String key) {
        this.key = key;
    }

    public static void main(String args[]) {
        System.setProperty("verbose:gc", "true");
        try {
            Map map = System.getProperties();

            for (; ; ) {
                map.put(new VerboseGcTest("key"), "value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
