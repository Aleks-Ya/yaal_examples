package lang.flow_control.break_operator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * break без метки воздействует на конструкции switch, while, do, or for.
 */
class WithoutLabelTest {
    @Test
    void forCycle() {
        int i = 0;
        for(;;i++) {
            break;
        }
        assertEquals(0, i);
    }

    @Test
    void whileCycle() {
        int i = 0;
        while (true) {
            i++;
            break;
        }
        assertEquals(1, i);
    }

    @Test
    void doCycle() {
        int i = 0;
        do {
            i++;
            break;
        } while (true);
        assertEquals(1, i);
    }

    @Test
    void switchh() {
        switch(2) {
            case 2: break;
            default:
                throw new AssertionError();
        }
    }
}