import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ExcludeTest {
    @Test(expected = ClassNotFoundException.class)
    public void test() throws ClassNotFoundException {
        assertNotNull(Class.forName("com.fasterxml.jackson.core.TreeNode"));
    }
}