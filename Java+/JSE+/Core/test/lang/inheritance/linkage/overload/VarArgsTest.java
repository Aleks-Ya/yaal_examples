package lang.inheritance.linkage.overload;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VarArgsTest {

    /**
     * JVM выберет самый специфичный метод.
     */
    @Test
    void mostSpecific() {
        assertThat(method(10, 20)).isEqualTo(3);
    }

    int method(int i, int... j) {
        return 1;
    }

    int method(int... i) {
        return 2;
    }

    int method(int i, int j) {
        return 3;
    }
}
