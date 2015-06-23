package flow_control.break_operator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * break с меткой позволяет перейти на любой оператор.
 */
public class WithLabel {

    /**
     * Прерываем блок кода, ограниченный {}.
     */
    @Test
    public void codeBlock() {
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
    public void twoCodeBlocks() {
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