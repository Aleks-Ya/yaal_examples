package variable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
