import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import org.junit.Test;

public class Example {

    /**
     * Не поддерживает сравнение массивов :(
     */
    @Test
    public void testName() {
        String[] s1 = {"a", "b"};
        String[] s2 = {"a", "c"};
        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(s1, s2);
        System.out.println(diff);
    }
}
