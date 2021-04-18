package lang.variable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Сокрытие локальных переменных.
 */
public class HideLocalVariables {
    @Test
    public void test() {
        Byte[] Byte[] = {{0}};
        assertTrue(Byte.toString().startsWith("[[Ljava.lang.Byte;@"));
        assertEquals("class java.lang.Byte", Byte.class.toString());
        assertEquals(1, Byte.length);
        assertEquals(8, new Byte("8").byteValue());
    }
}
