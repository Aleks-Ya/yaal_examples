package ru.yaal.examples.kubernates.configmap;

public class PrintEnvVariables {
    public static void main(String[] args) throws InterruptedException {
        printEnvVar("GOOD_COLOR");
        printEnvVar("BAD_COLOR");

        System.out.println();
        System.out.println("All environment variables:\n" + System.getenv());

        stayAlive();
    }

    private static void printEnvVar(String envVar ) {
        String goodColorValue = System.getenv(envVar);
        System.out.println(envVar + "=" + goodColorValue);
    }

    private static void stayAlive() throws InterruptedException {
        System.out.println();
        long n = 1;
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("Stop me #" + n);
            Thread.sleep(5000);
            n++;
        }
    }
}
