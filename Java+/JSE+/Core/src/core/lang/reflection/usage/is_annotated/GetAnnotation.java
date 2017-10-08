package core.lang.reflection.usage.is_annotated;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Использование метода Class#getAnnotation.
 */
public class GetAnnotation {
    @Test
    public void present() throws ClassNotFoundException {
        Class clazz = Class.forName("YesMarked");
        Assert.assertEquals(Marked.class, clazz.getAnnotation(Marked.class).annotationType());
    }

    @Test
    public void notPresent() throws ClassNotFoundException {
        Class clazz = Class.forName("NotMarked");
        Assert.assertNull(clazz.getAnnotation(Marked.class));
    }
}