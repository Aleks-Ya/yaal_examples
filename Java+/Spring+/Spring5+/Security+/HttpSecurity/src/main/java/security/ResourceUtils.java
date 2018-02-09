package security;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public final class ResourceUtils {

    public static String resourceToString(String resource, Class<?> clazz) throws IOException {
        Resource script = new ClassPathResource(resource, clazz);
        return Files.readAllLines(script.getFile().toPath()).stream().collect(Collectors.joining("\n"));
    }

    private ResourceUtils() {
    }
}
