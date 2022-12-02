package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReturnFromFinallyTest {
    @Test
    public void test() {
        assertThat(getInt1()).isEqualTo(10);
        assertThat(getInt2()).isEqualTo(20);
    }

    int getInt1() {
        int i = 0;
        try {
            i = 10;
        } finally {
            return i;
        }
    }

    int getInt2() {
        int i = 0;
        try {
            throw new Exception();
        } catch (Exception e) {
            i = 20;
        } finally {
            return i;
        }
    }
}
