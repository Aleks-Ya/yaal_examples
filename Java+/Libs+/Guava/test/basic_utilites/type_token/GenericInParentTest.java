package basic_utilites.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Generic ConcreteClass have no generics (they are in ParentClass).
 */
class GenericInParentTest {
    @Test
    void test() {
        var obj = new ConcreteClass();
        assertThat(obj.typeT.getRawType()).isEqualTo(Integer.class);
        assertThat(obj.typeD.getRawType()).isEqualTo(FileInputStream.class);
    }

    private static abstract class GenericClass<T extends Number, D extends InputStream> {
        TypeToken<T> typeT = new TypeToken<>(getClass()) {
        };
        TypeToken<D> typeD = new TypeToken<>(getClass()) {
        };
    }

    private static abstract class ParentClass extends GenericClass<Integer, FileInputStream> {
    }

    private static class ConcreteClass extends ParentClass {
    }
}
