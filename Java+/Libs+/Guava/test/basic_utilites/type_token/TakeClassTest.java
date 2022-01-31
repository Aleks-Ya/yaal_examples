package basic_utilites.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class fields in GenericClass.
 */
class TakeClassTest {
    @Test
    void test() {
        var obj = new ConcreteClass();
        assertThat(obj.classT).isEqualTo(Integer.class);
        assertThat(obj.classD).isEqualTo(FileInputStream.class);
    }

    private static abstract class GenericClass<T extends Number, D extends InputStream> {
        Class<? super T> classT = new TypeToken<T>(getClass()) {
        }.getRawType();
        Class<? super D> classD = new TypeToken<D>(getClass()) {
        }.getRawType();
    }

    private static class ConcreteClass extends GenericClass<Integer, FileInputStream> {
    }
}
