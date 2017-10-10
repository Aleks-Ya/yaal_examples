package lang.inheritance.linkage.overload;

import static java.lang.System.out;

/**
 * Перегружаемые методы имеют одинаковый параметр Integer и int.
 */
public class PrimitiveAndObjectArg {
    public static void main(String[] args) {
        method(1);
        method(new Integer(2));
    }

    private static void method(int i) {
        out.println("int " + i);
    }

    private static void method(Integer i) {
        out.println("Integer " + i);
    }
}