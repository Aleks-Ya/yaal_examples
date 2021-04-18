package lang.reflection.classloader;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Load, instantiate and invoke a method from a class absents in classpath.
 */
public class LoadClassesFromJarTest {

    @Test
    public void customClassLoader() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, InvocationTargetException, NoSuchMethodException {

        String name = "org.yaml.snakeyaml.DumperOptions";

        assertClassAbsent(name);

        SnakeYamlClassLoader cl = new SnakeYamlClassLoader();
        Class<?> clazz = cl.loadClass(name);

        Object snakeYaml = clazz.newInstance();
        Method getNameMethod = clazz.getMethod("isAllowUnicode");
        boolean result = (boolean) getNameMethod.invoke(snakeYaml);
        assertTrue(result);
    }

    private void assertClassAbsent(String name) {
        try {
            getClass().getClassLoader().loadClass(name);
        } catch (ClassNotFoundException e) {
            return;
        }
        throw new AssertionError("Class already loaded: " + name);
    }

    static class SnakeYamlClassLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(String className) throws ClassNotFoundException {
            byte[] b = loadClassData(className);
            return defineClass(className, b, 0, b.length);
        }

        private byte[] loadClassData(String name) throws ClassNotFoundException {
            String jarPath = getClass().getResource("snakeyaml-1.23.jar").getFile();
            try (JarFile jarFile = new JarFile(jarPath)) {
                String classPath = name.replaceAll("\\.", "/").concat(".class");
                JarEntry entry = jarFile.getJarEntry(classPath);
                return readEntry(jarFile, entry);
            } catch (IOException e) {
                throw new ClassNotFoundException("Class not found: " + name, e);
            }
        }

        private byte[] readEntry(JarFile jarFile, JarEntry entry) throws IOException {
            InputStream is = jarFile.getInputStream(entry);
            int entrySize = (int) entry.getSize();
            byte[] bytes = new byte[entrySize];
            BufferedInputStream bis = new BufferedInputStream(is);
            int biteNumber = bis.read(bytes, 0, entrySize);
            assert biteNumber == entry.getSize();
            return bytes;
        }
    }

}