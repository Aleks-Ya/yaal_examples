package basic_utilites.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Get generic class in runtime.
 */
class TypeTokenTest {
    @Test
    void test() {
        var obj = new ConcreteClass();
        assertThat(obj.typeT.getRawType()).isEqualTo(Integer.class);
        assertThat(obj.typeD.getRawType()).isEqualTo(String.class);
    }

    private static abstract class GenericClass<T, D> {
        TypeToken<T> typeT = new TypeToken<>(getClass()) {
        };
        TypeToken<D> typeD = new TypeToken<>(getClass()) {
        };
    }

    private static class ConcreteClass extends GenericClass<Integer, String> {
    }
}
