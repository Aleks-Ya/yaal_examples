package lang.method_handler;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.junit.Assert.assertEquals;

/**
 * Вызов метода с помощью MethodHandler.
 */
public class MethodHandlerTest {
    @Test
    public void testName() throws Throwable {
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findVirtual(String.class, "replace", mt);
        String s = (String) mh.invokeExact("daddy", 'd', 'n');
        assertEquals("nanny", s);
    }
}
