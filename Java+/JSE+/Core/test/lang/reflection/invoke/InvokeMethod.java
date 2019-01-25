package lang.reflection.invoke;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertThat;

public class InvokeMethod {

    @Test
    public void invokePrivateStaticMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<MyClass> clazz = MyClass.class;
        Method method = clazz.getDeclaredMethod("multiply", Integer.class, Integer.class);
        method.setAccessible(true);
        Integer result = (Integer) method.invoke(null, 2, 3);
        assertThat(result, Matchers.equalTo(6));
    }

    @Test
    public void invokePublicStaticMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<MyClass> clazz = MyClass.class;
        Method method = clazz.getMethod("toUpperCase", String.class);
        String result = (String) method.invoke(null, "abc");
        assertThat(result, Matchers.equalTo("ABC"));
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
