package lang.reflection.annotation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование метода Class#isAnnotationPresent.
 */
class IsAnnotationPresentTest {
    @Test
    void present() throws ClassNotFoundException, NoSuchFieldException {
        var clazz = Class.forName("lang.reflection.annotation.YesMarked");
        assertThat(clazz.isAnnotationPresent(Marked.class)).isTrue();

        var field = clazz.getDeclaredField("size");
        assertThat(field.isAnnotationPresent(Marked.class)).isTrue();
    }

    @Test
    void notPresent() throws ClassNotFoundException, NoSuchFieldException {
        var clazz = Class.forName("lang.reflection.annotation.NotMarked");
        assertThat(clazz.isAnnotationPresent(Marked.class)).isFalse();

        var field = clazz.getDeclaredField("size");
        assertThat(field.isAnnotationPresent(Marked.class)).isFalse();
    }
}