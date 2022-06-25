package memory.gc;

/**
 * Use "-XX:+PrintGCDetails" and "-XX:+PrintGCTimeStamps" JVM option.
 */
public class PrintGCDetails {
    public final String key;

    public PrintGCDetails(String key) {
        this.key = key;
    }

    public static void main(String[] args) {
        System.setProperty("verbose:gc", "true");
        try {
            var map = System.getProperties();
            for (; ; ) {
                map.put(new PrintGCDetails("key"), "value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
