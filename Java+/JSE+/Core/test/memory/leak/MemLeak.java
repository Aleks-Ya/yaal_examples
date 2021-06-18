package memory.leak;

public class MemLeak {
    public final String key;

    public MemLeak(String key) {
        this.key = key;
    }

    public static void main(String[] args) {
        try {
            var map = System.getProperties();
            for (; ; ) {
                map.put(new MemLeak("key"), "value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
