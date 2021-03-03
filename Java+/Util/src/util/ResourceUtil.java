package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.String.format;

public class ResourceUtil {

    private ResourceUtil() {
    }

    public static String resourceToString(Class<?> clazz, String resourceName) {
        try {
            URL resourceUrl = clazz.getResource(resourceName);
            Path path = new File(resourceUrl.getFile()).toPath();
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String resourceToString(String resourceName) {
        try {
            ClassLoader classLoader = ResourceUtil.class.getClassLoader();
            URL resourceUrl = classLoader.getResource(resourceName);
            if (resourceUrl == null) {
                throw new IOException(format("Resource '%s' not found in '%s'", classLoader, resourceName));
            }
            Path path = new File(resourceUrl.getFile()).toPath();
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static File resourceToFile(Class<?> clazz, String resourceName) {
        URL resourceUrl = clazz.getResource(resourceName);
        return new File(resourceUrl.getFile());
    }

    public static String resourceToPath(Class<?> clazz, String resourceName) {
        URL resourceUrl = clazz.getResource(resourceName);
        return resourceUrl.getFile();
    }

    public static InputStream resourceToInputStream(Class<?> clazz, String resourceName) {
        return clazz.getResourceAsStream(resourceName);
    }

    public static InputStream resourceToInputStream(String resourceName) {
        return ResourceUtil.class.getClassLoader().getResourceAsStream(resourceName);
    }

}
