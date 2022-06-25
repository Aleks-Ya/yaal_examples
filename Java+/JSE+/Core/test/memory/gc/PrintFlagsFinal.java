package memory.gc;

/**
 * Use "-XX:+PrintFlagsFinal" JVM option.
 */
public class PrintFlagsFinal {
    public final String key;

    public PrintFlagsFinal(String key) {
        this.key = key;
    }

    public static void main(String[] args) {
        System.setProperty("verbose:gc", "true");
        try {
            var map = System.getProperties();
            for (; ; ) {
                map.put(new PrintFlagsFinal("key"), "value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
