package lang.reflection.classloader;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;


public class FindJarFromWhichClassWasLoaded {

    /**
     * Find JAR from which a class was loaded.
     */
    @Test
    public void findSourceJar() {
        Class klass = org.apache.commons.io.FileUtils.class;
        CodeSource codeSource = klass.getProtectionDomain().getCodeSource();
        if (codeSource != null) {
            System.out.println(codeSource.getLocation());
        }
    }

    /**
     * Print all loaded JARs.
     */
    @Test
    public void printAllClassJars() {
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        URL[] urLs = ((URLClassLoader) sysClassLoader).getURLs();
        for (URL url : urLs) {
            System.out.println(url.getFile());
        }
    }
}