package lang.flow_control.break_operator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * break с меткой позволяет перейти на любой оператор.
 */
class WithLabelTest {

    /**
     * Прерываем блок кода, ограниченный {}.
     */
    @Test
    void codeBlock() {
        int a;
        block:
        {
            a = 1;
            break block;
        }
        assertThat(a).isEqualTo(1);
    }

    /**
     * Прерываем блок кода, ограниченный {}.
     */
    @Test
    void twoCodeBlocks() {
        int a;
        outer:
        {
            block:
            {
                a = 1;
                break outer;
            }
        }
        assertThat(a).isEqualTo(1);
    }
}