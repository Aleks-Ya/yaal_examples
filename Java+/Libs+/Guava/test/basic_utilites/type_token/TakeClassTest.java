package basic_utilites.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class fields in GenericClass.
 */
public class TakeClassTest {
    @Test
    public void test() {
        ConcreteClass obj = new ConcreteClass();
        assertEquals(Integer.class, obj.classT);
        assertEquals(FileInputStream.class, obj.classD);
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
