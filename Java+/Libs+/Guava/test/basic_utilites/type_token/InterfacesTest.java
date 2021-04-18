package basic_utilites.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Interfaces anywhere.
 */
public class InterfacesTest {
    @Test
    public void test() {
        ConcreteClass obj = new ConcreteClass();
        assertEquals(MyClass.class, obj.classT);
    }

    private static abstract class GenericClass<T extends MyInterface> {
        Class<? super T> classT = new TypeToken<T>(getClass()) {
        }.getRawType();
    }

    private static abstract class ParentClass extends GenericClass<MyClass> {
    }

    private static class ConcreteClass extends ParentClass {
    }

    private interface MyInterface {
    }

    private static abstract class MyAbstract implements MyInterface {
    }

    private static class MyClass extends MyAbstract {
    }
}
