package memory.gc;

/**
 * Use "-verbose:gc" and "-XX:+PrintGCTimeStamps" JVM options.
 */
public class VerboseGc {
    public final String key;

    public VerboseGc(String key) {
        this.key = key;
    }

    public static void main(String[] args) {
        System.setProperty("verbose:gc", "true");
        try {
            var map = System.getProperties();
            for (; ; ) {
                map.put(new VerboseGc("key"), "value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
