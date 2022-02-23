package lang.reflection.classloader;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Динамическая загрузка класса из jar с помощью URLClassLoader.
 */
class LoadClassFromJarTest {
    @Test
    void loadClass() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var plugin = getClass().getResource("StringPlugin.jar");
        var loader = new URLClassLoader(new URL[]{plugin}, getClass().getClassLoader());
        var clazz = Class.forName("lang.reflection.classloader.StringInversion", true, loader);
        var method = clazz.getDeclaredMethod("invertString", String.class);
        var actual = (String) method.invoke(null, "abc");
        assertThat(actual).isEqualTo("cba");
    }
}
