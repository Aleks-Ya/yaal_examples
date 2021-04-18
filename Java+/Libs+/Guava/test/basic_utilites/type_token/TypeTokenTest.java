package basic_utilites.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Get generic class in runtime.
 */
public class TypeTokenTest {
    @Test
    public void test() {
        ConcreteClass obj = new ConcreteClass();
        assertEquals(Integer.class, obj.typeT.getRawType());
        assertEquals(String.class, obj.typeD.getRawType());
    }

    private static abstract class GenericClass<T, D> {
        TypeToken<T> typeT = new TypeToken<T>(getClass()) {
        };
        TypeToken<D> typeD = new TypeToken<D>(getClass()) {
        };
    }

    private static class ConcreteClass extends GenericClass<Integer, String> {
    }
}
