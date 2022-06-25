package lang.reflection.classloader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URLClassLoader;


class FindJarFromWhichClassWasLoadedTest {

    /**
     * Find JAR from which a class was loaded.
     */
    @Test
    void findSourceJar() {
        Class<?> klass = org.apache.commons.io.FileUtils.class;
        var codeSource = klass.getProtectionDomain().getCodeSource();
        if (codeSource != null) {
            System.out.println(codeSource.getLocation());
        }
    }

    /**
     * Print all loaded JARs.
     */
    @Test
    @Disabled("Not work in Java >=9")
    void printAllClassJars() {
        var sysClassLoader = org.apache.commons.io.FileUtils.class.getClassLoader();
        var urLs = ((URLClassLoader) sysClassLoader).getURLs();
        for (var url : urLs) {
            System.out.println(url.getFile());
        }
    }
}