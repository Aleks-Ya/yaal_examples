package basic_utilites.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Generic extends: <T extends Number>.
 */
public class GenericExtendsTest {
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

    private static class ConcreteClass extends GenericClass<Integer, FileInputStream> {
    }
}
