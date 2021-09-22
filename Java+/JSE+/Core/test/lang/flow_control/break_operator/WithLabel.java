package lang.flow_control.break_operator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * break с меткой позволяет перейти на любой оператор.
 */
class WithLabel {

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
        assertEquals(1, a);
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
        assertEquals(1, a);
    }
}