package lang.variable;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

/**
 * Имена переменных Java.
 */
class Variables {
    @Test
    void correctNames() {
        int i = 1;
        int $ = 2;
        out.println(i + $);
    }
}
