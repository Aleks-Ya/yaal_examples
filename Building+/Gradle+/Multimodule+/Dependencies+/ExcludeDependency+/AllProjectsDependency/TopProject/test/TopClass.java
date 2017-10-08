import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TopClass {
    @Test
    public void test() throws ClassNotFoundException {
        assertNotNull(Class.forName("org.joda.time.DateTime"));
    }
}