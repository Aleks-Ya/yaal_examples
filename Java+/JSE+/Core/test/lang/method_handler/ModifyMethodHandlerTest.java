package lang.method_handler;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.assertj.core.api.Assertions.assertThat;

class ModifyMethodHandlerTest {
    @Test
    void dropArguments() throws Throwable {
        var mt = MethodType.methodType(String.class, String.class);
        var cat = MethodHandles.lookup().findVirtual(String.class, "concat", mt);
        assertThat((String) cat.invokeExact("x", "y")).isEqualTo("xy");
        var d0 = MethodHandles.dropArguments(cat, 0, String.class);
        assertThat((String) d0.invokeExact("x", "y", "z")).isEqualTo("yz");
        var d1 = MethodHandles.dropArguments(cat, 1, String.class);
        assertThat((String) d1.invokeExact("x", "y", "z")).isEqualTo("xz");
        var d2 = MethodHandles.dropArguments(cat, 2, String.class);
        assertThat((String) d2.invokeExact("x", "y", "z")).isEqualTo("xy");
        var d12 = MethodHandles.dropArguments(cat, 1, int.class, boolean.class);
        assertThat((String) d12.invokeExact("x", 12, true, "z")).isEqualTo("xz");
    }
}
