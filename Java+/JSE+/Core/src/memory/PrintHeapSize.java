package memory;


/**
 * Prints heap size (configured by "-Xmx1g" option).
 */
public class PrintHeapSize {
    public static void main(String[] args) {
        var bytes = Runtime.getRuntime().maxMemory();
        var megabytes = bytes / 1024 / 1024;
        System.out.printf("Heap size: %d Mb\n", megabytes);
    }
}
