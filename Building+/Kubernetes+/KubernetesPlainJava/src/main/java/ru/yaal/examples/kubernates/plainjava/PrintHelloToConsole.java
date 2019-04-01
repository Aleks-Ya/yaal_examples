package ru.yaal.examples.kubernates.plainjava;

public class PrintHelloToConsole {
    public static void main(String[] args) throws InterruptedException {
        long n = 1;
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("Hello, Plain Java Kubernates! #" + n);
            Thread.sleep(5000);
            n++;
        }
    }
}
