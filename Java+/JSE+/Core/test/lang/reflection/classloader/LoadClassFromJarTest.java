package lang.reflection.classloader;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Динамическая загрузка класса из jar с помощью URLClassLoader.
 */
public class LoadClassFromJarTest {
    @Test
    public void loadClass()
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        URL plugin = getClass().getResource("StringPlugin.jar");
        URLClassLoader loader = new URLClassLoader(new URL[]{plugin}, getClass().getClassLoader());
        Class<?> clazz = Class.forName("lang.reflection.classloader.StringInversion", true, loader);
        Method method = clazz.getDeclaredMethod("invertString", String.class);
        String actual = (String) method.invoke(null, "abc");
        assertThat(actual, equalTo("cba"));
    }
}
