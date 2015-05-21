package reflection.usage.is_annotated;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Использование метода Class#isAnnotationPresent.
 */
public class IsAnnotationPresent {
    @Test
    public void present() throws ClassNotFoundException, NoSuchFieldException {
        Class clazz = Class.forName("YesMarked");
        assertTrue(clazz.isAnnotationPresent(Marked.class));

        Field field = clazz.getDeclaredField("size");
        assertTrue(field.isAnnotationPresent(Marked.class));
    }

    @Test
    public void notPresent() throws ClassNotFoundException, NoSuchFieldException {
        Class clazz = Class.forName("NotMarked");
        assertFalse(clazz.isAnnotationPresent(Marked.class));

        Field field = clazz.getDeclaredField("size");
        assertFalse(field.isAnnotationPresent(Marked.class));
    }
}