package java8.method_reference;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MethodReferenceTest {

    private static void appendPlus(StringBuilder sb) {
        sb.append("+++");
    }

    /**
     * Reference to a Static Method
     */
    @Test
    void staticMethod() {
        List<StringBuilder> list = Arrays.asList(
                new StringBuilder("a"),
                new StringBuilder("b"),
                new StringBuilder("c"));
        list.forEach(MethodReferenceTest::appendPlus);
        list.forEach(sb -> assertThat(sb.toString().endsWith("+++")).isTrue());
    }

    /**
     * Reference to an Instance Method of a Particular Object.
     */
    @Test
    void methodOfParticularObject() {
        List<String> list = Arrays.asList("a", "b", "c");
        PrintStream out = System.out;
        list.forEach(out::println);
        list.forEach(System.out::println);
    }

    /**
     * Reference to an Instance Method of an Arbitrary Object of a Particular Type.
     */
    @Test
    void methodOfParticularObjectOfParticularType() {
        String[] arr = new String[]{"b", "c", "a"};
        Arrays.sort(arr, String::compareToIgnoreCase);
        assertThat(arr).isEqualTo(new String[]{"a", "b", "c"});
    }

    /**
     * Reference to a Constructor.
     */
    @Test
    void constructor() {
        List<String> list = Arrays.asList("a", "b", "c");
        list.forEach(StringBuilder::new);
    }
}
