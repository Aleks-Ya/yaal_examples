package lang.inheritance.linkage.overload;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        print('a');
    }

    private static void print(byte n) {
        out.println("byte");
    }

    private static void print(int n) {
        out.println("int");
    }

    private static void print(long n) {
        out.println("long");
    }
}