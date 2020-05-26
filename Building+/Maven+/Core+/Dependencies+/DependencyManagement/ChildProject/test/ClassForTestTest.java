import org.junit.Test;

import static org.junit.Assert.*;

public class ClassForTestTest {

    @Test
    public void test() throws Exception {
        assertEquals("text", ClassForTest.getTest());
    }
}