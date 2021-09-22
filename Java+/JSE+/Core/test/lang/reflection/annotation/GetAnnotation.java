package lang.reflection.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Использование метода Class#getAnnotation.
 */
class GetAnnotation {
    @Test
    void present() throws ClassNotFoundException {
        Class clazz = Class.forName("YesMarked");
        assertEquals(Marked.class, clazz.getAnnotation(Marked.class).annotationType());
    }

    @Test
    void notPresent() throws ClassNotFoundException {
        Class clazz = Class.forName("NotMarked");
        assertNull(clazz.getAnnotation(Marked.class));
    }
}