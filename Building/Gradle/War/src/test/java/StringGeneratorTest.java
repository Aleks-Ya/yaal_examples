import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class StringGeneratorTest {

    @Test
    public void testGetString() throws Exception {
        assertEquals(StringGenerator.getString(), "Just do it!");
    }
}