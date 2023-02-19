package lang.method_handler;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.assertj.core.api.Assertions.assertThat;

class CreateMethodHandlerTest {
    @Test
    void invoke() throws Throwable {
        var mt = MethodType.methodType(String.class, char.class, char.class);
        var lookup = MethodHandles.lookup();
        var mh = lookup.findVirtual(String.class, "replace", mt);
        var s = (String) mh.invokeExact("daddy", 'd', 'n');
        assertThat(s).isEqualTo("nanny");
    }

    @Test
    void methodToHandler() throws Throwable {
        var replaceMethod = String.class.getMethod("replace", char.class, char.class);
        var lookup = MethodHandles.lookup();
        var mh = lookup.unreflect(replaceMethod);
        var s = (String) mh.invokeExact("daddy", 'd', 'n');
        assertThat(s).isEqualTo("nanny");
    }
}
