package import_operator;

import org.junit.Test;

import java.io.IOException;

import static java.lang.String.format;

/**
 * Дублированный оператор импорта допустим.
 */
public class DoubleImport {

    @Test
    public void main() {
        System.out.println(new IOException());
        System.out.println(format(""));
    }
}