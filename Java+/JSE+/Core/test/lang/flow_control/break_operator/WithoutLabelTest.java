package lang.flow_control.break_operator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * break без метки воздействует на конструкции switch, while, do, or for.
 */
class WithoutLabelTest {
    @Test
    void forCycle() {
        int i = 0;
        for (; ; i++) {
            break;
        }
        assertThat(i).isEqualTo(0);
    }

    @Test
    void whileCycle() {
        int i = 0;
        while (true) {
            i++;
            break;
        }
        assertThat(i).isEqualTo(1);
    }

    @Test
    void doCycle() {
        int i = 0;
        do {
            i++;
            break;
        } while (true);
        assertThat(i).isEqualTo(1);
    }

    @Test
    void switchh() {
        switch (2) {
            case 2:
                break;
            default:
                throw new AssertionError();
        }
    }
}