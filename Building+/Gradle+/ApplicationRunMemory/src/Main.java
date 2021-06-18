public class Main {
    public static void main(String[] args) {
        System.out.printf("Heap size: %d Mb\n", Runtime.getRuntime().maxMemory() / 1024 / 1024);
    }
}