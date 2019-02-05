package lang.reflection.classloader;

import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;


public class PrintClassLoaderParents {

    @Test
    public void printClassLoaderParents() {
        Class<?> clazz = getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        while (classLoader != null) {
            String classLoaderName = classLoader.getClass().getName();
            System.out.println(classLoaderName);
            classLoader = classLoader.getParent();
        }
    }
}