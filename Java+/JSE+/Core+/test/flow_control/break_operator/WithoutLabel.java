package flow_control.break_operator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * break без метки воздействует на конструкции switch, while, do, or for.
 */
public class WithoutLabel {
    @Test
    public void forCycle() {
        int i = 0;
        for(;;i++) {
            break;
        }
        assertEquals(0, i);
    }

    @Test
    public void whileCycle() {
        int i = 0;
        while (true) {
            i++;
            break;
        }
        assertEquals(1, i);
    }

    @Test
    public void doCycle() {
        int i = 0;
        do {
            i++;
            break;
        } while (true);
        assertEquals(1, i);
    }

    @Test
    public void switchh() {
        switch(2) {
            case 2: break;
            default:
                throw new AssertionError();
        }
    }
}