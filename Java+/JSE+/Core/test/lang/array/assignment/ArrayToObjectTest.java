package lang.array.assignment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Присвоение массива переменной Object.
 */
class ArrayToObjectTest {
    @Test
    void object() {
        int[] a = {1, 2};

        Object o = a;
        assertThat(o.getClass().isArray()).isTrue();
        assertThat(o.getClass().toString()).isEqualTo("class [I");

        int[] b = (int[]) o;
        assertThat(a).isEqualTo(b);
    }
}