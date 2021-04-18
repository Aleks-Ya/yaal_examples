package java8.method_reference;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodReference {

    /**
     * Reference to a Static Method
     */
    @Test
    public void staticMethod() {
        List<StringBuilder> list = Arrays.asList(
                new StringBuilder("a"),
                new StringBuilder("b"),
                new StringBuilder("c"));
        list.forEach(MethodReference::appendPlus);
        list.forEach(sb -> assertTrue(sb.toString().endsWith("+++")));
    }

    private static void appendPlus(StringBuilder sb) {
        sb.append("+++");
    }

    /**
     * Reference to an Instance Method of a Particular Object.
     */
    @Test
    public void methodOfParticularObject() {
        List<String> list = Arrays.asList("a", "b", "c");
        PrintStream out = System.out;
        list.forEach(out::println);
        list.forEach(System.out::println);
    }

    /**
     * Reference to an Instance Method of an Arbitrary Object of a Particular Type.
     */
    @Test
    public void methodOfParticularObjectOfParticularType() {
        String[] arr = new String[]{"b", "c", "a"};
        Arrays.sort(arr, String::compareToIgnoreCase);
        assertArrayEquals(new String[]{"a", "b", "c"}, arr);
    }

    /**
     * Reference to a Constructor.
     */
    @Test
    public void constructor() {
        List<String> list = Arrays.asList("a", "b", "c");
        list.forEach(StringBuilder::new);
    }
}
