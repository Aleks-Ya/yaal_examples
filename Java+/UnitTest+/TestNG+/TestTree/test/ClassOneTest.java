import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ClassOneTest {
    @Test
    public void testGetStr() throws Exception {
        ClassOne one = new ClassOne();
        assertEquals(one.getStr(), "val");
    }
}
