package lang.operator.plus;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class PlusTest {

    /**
     * Конкатенция String и char.
     */
    @Test
    void stringCharConcat() {
        out.println("lang/string " + 'c');
    }
}