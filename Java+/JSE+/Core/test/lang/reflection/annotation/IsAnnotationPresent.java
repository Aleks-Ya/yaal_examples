package lang.reflection.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Использование метода Class#isAnnotationPresent.
 */
class IsAnnotationPresent {
    @Test
    void present() throws ClassNotFoundException, NoSuchFieldException {
        var clazz = Class.forName("lang.reflection.annotation.YesMarked");
        assertTrue(clazz.isAnnotationPresent(Marked.class));

        var field = clazz.getDeclaredField("size");
        assertTrue(field.isAnnotationPresent(Marked.class));
    }

    @Test
    void notPresent() throws ClassNotFoundException, NoSuchFieldException {
        var clazz = Class.forName("lang.reflection.annotation.NotMarked");
        assertFalse(clazz.isAnnotationPresent(Marked.class));

        var field = clazz.getDeclaredField("size");
        assertFalse(field.isAnnotationPresent(Marked.class));
    }
}