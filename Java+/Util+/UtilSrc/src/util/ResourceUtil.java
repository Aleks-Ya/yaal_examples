package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class ResourceUtil {

    private ResourceUtil() {
    }

    public static String resourceToString(Class<?> clazz, String resourceName) {
        try {
            var resourceUrl = requireNonNull(clazz.getResource(resourceName));
            var path = new File(resourceUrl.getFile()).toPath();
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static URL resourceToUrl(Class<?> clazz, String resourceName) {
        try {
            var resourceUrl = clazz.getResource(resourceName);
            if (resourceUrl == null) {
                throw new IOException(format("Resource '%s' not found", resourceName));
            }
            return resourceUrl;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static URL resourceToUrl(String resourceName) {
        try {
            var classLoader = ResourceUtil.class.getClassLoader();
            var resourceUrl = classLoader.getResource(resourceName);
            if (resourceUrl == null) {
                throw new IOException(format("Resource '%s' not found in '%s'", classLoader, resourceName));
            }
            return resourceUrl;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String resourceToStrUrl(String resourceName) {
        return resourceToUrl(resourceName).toString();
    }

    public static String resourceToString(String resourceName) {
        try {
            var path = new File(resourceToUrl(resourceName).getFile()).toPath();
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<String> resourceToStringList(String resourceName) {
        try {
            return Files.readAllLines(resourceToPath(resourceName));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static File resourceToFile(Class<?> clazz, String resourceName) {
        var resourceUrl = requireNonNull(clazz.getResource(resourceName));
        return new File(resourceUrl.getFile());
    }

    public static File resourceToFile(String resourceName) {
        return new File(resourceToUrl(resourceName).getFile());
    }

    public static String resourceToStrPath(String resourceName) {
        return requireNonNull(ResourceUtil.class.getClassLoader().getResource(resourceName)).getFile();
    }

    public static String resourceToStrPath(Class<?> clazz, String resourceName) {
        return requireNonNull(clazz.getResource(resourceName)).getFile();
    }

    public static Path resourceToPath(String resourceName) {
        return resourceToFile(resourceName).toPath();
    }

    public static Path resourceToPath(Class<?> clazz, String resourceName) {
        return resourceToFile(clazz, resourceName).toPath();
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
