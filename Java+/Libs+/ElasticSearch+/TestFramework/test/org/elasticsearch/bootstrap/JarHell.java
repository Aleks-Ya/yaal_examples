package org.elasticsearch.bootstrap;

import org.elasticsearch.common.SuppressForbidden;
import org.elasticsearch.common.io.PathUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Disables jar hell check forced by ElasticSearch Test Framework.
 * Shadows {@link JarHell#checkJarHell(Consumer)} implementation
 * from "org.elasticsearch.test:framework".
 */
@SuppressWarnings("unused")
public class JarHell {
    public static void checkJarHell(Consumer<String> output) {
        System.out.println("Skip jar hell check.");
    }

    public static Set<URL> parseClassPath() {
        return parseClassPath(System.getProperty("java.class.path"));
    }

    @SuppressForbidden(reason = "resolves against CWD because that is how classpaths work")
    static Set<URL> parseClassPath(String classPath) {
        String pathSeparator = System.getProperty("path.separator");
        String fileSeparator = System.getProperty("file.separator");
        String elements[] = classPath.split(pathSeparator);
        Set<URL> urlElements = new LinkedHashSet<>(); // order is already lost, but some filesystems have it
        for (String element : elements) {
            // Technically empty classpath element behaves like CWD.
            // So below is the "correct" code, however in practice with ES, this is usually just a misconfiguration,
            // from old shell scripts left behind or something:
            //   if (element.isEmpty()) {
            //      element = System.getProperty("user.dir");
            //   }
            // Instead we just throw an exception, and keep it clean.
            if (element.isEmpty()) {
                throw new IllegalStateException("Classpath should not contain empty elements! (outdated shell script from a previous" +
                        " version?) classpath='" + classPath + "'");
            }
            // we should be able to just Paths.get() each element, but unfortunately this is not the
            // whole story on how classpath parsing works: if you want to know, start at sun.misc.Launcher,
            // be sure to stop before you tear out your eyes. we just handle the "alternative" filename
            // specification which java seems to allow, explicitly, right here...
            if (element.startsWith("/") && "\\".equals(fileSeparator)) {
                // "correct" the entry to become a normal entry
                // change to correct file separators
                element = element.replace("/", "\\");
                // if there is a drive letter, nuke the leading separator
                if (element.length() >= 3 && element.charAt(2) == ':') {
                    element = element.substring(1);
                }
            }
            // now just parse as ordinary file
            try {
                if (element .equals("/")) {
                    // Eclipse adds this to the classpath when running unit tests...
                    continue;
                }
                URL url = PathUtils.get(element).toUri().toURL();
                // junit4.childvm.count
                if (urlElements.add(url) == false && element.endsWith(".jar")) {
                    throw new IllegalStateException("jar hell!" + System.lineSeparator() +
                            "duplicate jar [" + element + "] on classpath: " + classPath);
                }
            } catch (MalformedURLException e) {
                // should not happen, as we use the filesystem API
                throw new RuntimeException(e);
            }
        }
        return Collections.unmodifiableSet(urlElements);
    }
}
