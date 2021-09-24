package cluster;

import java.io.File;
import java.util.Objects;

public class ResourceUtil {

    private ResourceUtil() {
    }

    public static File resourceToFile(Class<?> clazz, String resourceName) {
        var resourceUrl = Objects.requireNonNull(clazz.getResource(resourceName));
        return new File(resourceUrl.getFile());
    }
}
