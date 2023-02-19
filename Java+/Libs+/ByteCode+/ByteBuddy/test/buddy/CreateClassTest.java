package buddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class CreateClassTest {
    @Test
    void create() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
        try (var unloaded = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()) {
            var dynamicType = unloaded.load(getClass().getClassLoader()).getLoaded();
            assertThat(dynamicType.getDeclaredConstructor().newInstance().toString()).isEqualTo("Hello World!");
        }
    }
}