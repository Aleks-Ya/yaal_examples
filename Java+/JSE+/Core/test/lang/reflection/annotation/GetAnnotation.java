package lang.reflection.annotation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование метода Class#getAnnotation.
 */
class GetAnnotation {
    @Test
    void present() {
        assertThat(YesMarked.class.getAnnotation(Marked.class).annotationType()).isEqualTo(Marked.class);
    }

    @Test
    void notPresent() {
        assertThat(NotMarked.class.getAnnotation(Marked.class)).isNull();
    }
}