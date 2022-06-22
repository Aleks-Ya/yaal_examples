package lang.reflection.method;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class InvokeMethodTest {

    @Test
    void invokePrivateStaticMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var clazz = MyClass.class;
        var method = clazz.getDeclaredMethod("multiply", Integer.class, Integer.class);
        method.setAccessible(true);
        var result = (Integer) method.invoke(null, 2, 3);
        assertThat(result).isEqualTo(6);
    }

    @Test
    void invokePublicStaticMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var clazz = MyClass.class;
        var method = clazz.getMethod("toUpperCase", String.class);
        var result = (String) method.invoke(null, "abc");
        assertThat(result).isEqualTo("ABC");
    }

    private static class MyClass {
        private static Integer multiply(Integer a, Integer b) {
            return a * b;
        }

        @SuppressWarnings("WeakerAccess")
        public static String toUpperCase(String s) {
            return s.toUpperCase();
        }
    }
}
