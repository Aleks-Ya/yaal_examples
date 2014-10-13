import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Использование метода Class#isAnnotationPresent.
 */
public class IsAnnotationPresent {
    @Test
    public void present() throws ClassNotFoundException {
        Class clazz = Class.forName("YesMarked");
        assertTrue(clazz.isAnnotationPresent(Marked.class));
    }

    @Test
    public void notPresent() throws ClassNotFoundException {
        Class clazz = Class.forName("NotMarked");
        assertFalse(clazz.isAnnotationPresent(Marked.class));
    }
}