package lang.system;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Перевод строки.
 */
public class LineSeparator {

    @Test
    public void test() {
        String separator = System.getProperty("line.separator");
        assertTrue("\n".equals(separator) || "\r\n".equals(separator));
    }
}