package core.lang.reflection.class_loader;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Динамическая загрузка класса из jar.
 */
public class LoadClassFromJarTest {
    @Test
    public void loadClass()
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        URL plugin = getClass().getResource("StringPlugin.jar");
        URLClassLoader loader = new URLClassLoader(new URL[]{plugin}, getClass().getClassLoader());
        Class<?> clazz = Class.forName("lang.reflection.class_loader.StringInversion", true, loader);
        Method method = clazz.getDeclaredMethod("invertString", String.class);
        String actual = (String) method.invoke(null, "abc");
        Assert.assertThat(actual, IsEqual.equalTo("cba"));
    }
}
