package scanners;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Зависимости между тестами в одном классе.
 */
public class DependsOnMethod {
    private int a;
    private int b;

    @Test
    void testA() {
        assertEquals(0, a);
        a = 1;
    }

    @Test
    void testB() {
        assertEquals(0, b);
        b = 2;
    }

    @Test(dependsOnMethods = {"testA", "testB"})
    public void testDepends() {
        assertEquals(1, a);
        assertEquals(2, b);
    }
}
