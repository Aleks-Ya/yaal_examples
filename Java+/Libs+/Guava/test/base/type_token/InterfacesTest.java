package base.type_token;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Interfaces anywhere.
 */
class InterfacesTest {
    @Test
    void test() {
        var obj = new ConcreteClass();
        assertThat(obj.classT).isEqualTo(MyClass.class);
    }

    private interface MyInterface {
    }

    private static abstract class GenericClass<T extends MyInterface> {
        Class<? super T> classT = new TypeToken<T>(getClass()) {
        }.getRawType();
    }

    private static abstract class ParentClass extends GenericClass<MyClass> {
    }

    private static class ConcreteClass extends ParentClass {
    }

    private static abstract class MyAbstract implements MyInterface {
    }

    private static class MyClass extends MyAbstract {
    }
}
