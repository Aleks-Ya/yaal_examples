package lang.reflection.classloader;

import org.junit.jupiter.api.Test;


class PrintClassLoaderParentsTest {

    @Test
    void printClassLoaderParents() {
        Class<?> clazz = getClass();
        var classLoader = clazz.getClassLoader();
        while (classLoader != null) {
            var classLoaderName = classLoader.getClass().getName();
            System.out.println(classLoaderName);
            classLoader = classLoader.getParent();
        }
    }
}