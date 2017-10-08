package core.lang.variable;

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
        Assert.assertTrue(Byte.toString().startsWith("[[Ljava.lang.Byte;@"));
        Assert.assertEquals("class java.lang.Byte", Byte.class.toString());
        Assert.assertEquals(1, Byte.length);
        Assert.assertEquals(8, new Byte("8").byteValue());
    }
}
