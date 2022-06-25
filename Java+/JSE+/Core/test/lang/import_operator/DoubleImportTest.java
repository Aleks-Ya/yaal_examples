package lang.import_operator;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.lang.String.format;

/**
 * Дублированный оператор импорта допустим.
 */
class DoubleImportTest {

    @Test
    void main() {
        System.out.println(new IOException());
        System.out.println(format(""));
    }
}