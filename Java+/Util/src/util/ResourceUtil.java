package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

import static java.lang.String.format;

public class ResourceUtil {

    private ResourceUtil() {
    }

    public static String resourceToString(Class<?> clazz, String resourceName) {
        try {
            var resourceUrl = Objects.requireNonNull(clazz.getResource(resourceName));
            var path = new File(resourceUrl.getFile()).toPath();
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String resourceToString(String resourceName) {
        try {
            var classLoader = ResourceUtil.class.getClassLoader();
            var resourceUrl = classLoader.getResource(resourceName);
            if (resourceUrl == null) {
                throw new IOException(format("Resource '%s' not found in '%s'", classLoader, resourceName));
            }
            var path = new File(resourceUrl.getFile()).toPath();
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static File resourceToFile(Class<?> clazz, String resourceName) {
        var resourceUrl = Objects.requireNonNull(clazz.getResource(resourceName));
        return new File(resourceUrl.getFile());
    }

    public static String resourceToPath(Class<?> clazz, String resourceName) {
        var resourceUrl = Objects.requireNonNull(clazz.getResource(resourceName));
        return resourceUrl.getFile();
    }

    public static InputStream resourceToInputStream(Class<?> clazz, String resourceName) {
        return clazz.getResourceAsStream(resourceName);
    }

    public static InputStream resourceToInputStream(String resourceName) {
        return ResourceUtil.class.getClassLoader().getResourceAsStream(resourceName);
    }

    public static byte[] resourceToBytes(Class<?> clazz, String resourceName) {
        try {
            return Files.readAllBytes(resourceToFile(clazz, resourceName).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
