package lang.variable;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Сокрытие локальных переменных.
 */
class HideLocalVariablesTest {
    @Test
    void test() {
        Byte[] Byte[] = {{0}};
        assertThat(Byte.toString().startsWith("[[Ljava.lang.Byte;@")).isTrue();
        assertThat(Byte.class.toString()).isEqualTo("class java.lang.Byte");
        assertThat(Byte.length).isEqualTo(1);
    }
}
