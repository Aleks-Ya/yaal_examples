package debug;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int c = 1;
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("Waiting " + c++);
            Thread.sleep(5000);
        }
    }
}
