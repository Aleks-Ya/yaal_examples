package basic_utilites.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Generic ConcreteClass have no generics (they are in ParentClass).
 */
public class GenericInParentTest {
    @Test
    public void test() {
        ConcreteClass obj = new ConcreteClass();
        assertEquals(Integer.class, obj.typeT.getRawType());
        assertEquals(FileInputStream.class, obj.typeD.getRawType());
    }

    private static abstract class GenericClass<T extends Number, D extends InputStream> {
        TypeToken<T> typeT = new TypeToken<T>(getClass()) {
        };
        TypeToken<D> typeD = new TypeToken<D>(getClass()) {
        };
    }

    private static abstract class ParentClass extends GenericClass<Integer, FileInputStream> {
    }

    private static class ConcreteClass extends ParentClass {
    }
}
