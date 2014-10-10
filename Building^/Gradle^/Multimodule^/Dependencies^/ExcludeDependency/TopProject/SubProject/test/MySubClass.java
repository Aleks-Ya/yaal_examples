import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MySubClass {
    @Test
    public void main() throws ClassNotFoundException {
        assertNotNull(Class.forName("org.joda.time.DateTime"));
    }
}