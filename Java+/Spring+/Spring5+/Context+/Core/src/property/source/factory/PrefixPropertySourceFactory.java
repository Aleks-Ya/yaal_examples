package property.source.factory;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Arrays;

import static java.util.stream.Collectors.toMap;

class PrefixPropertySourceFactory implements PropertySourceFactory {
    private static final String PROPERTY_NAME_PREFIX_PREFIX = "my.";

    @Override
    @NonNull
    public PropertySource<?> createPropertySource(@Nullable String name, @NonNull EncodedResource resource) throws IOException {
        var resourcePropertySource = name != null ? new ResourcePropertySource(name, resource) : new ResourcePropertySource(resource);
        var map = Arrays.stream(resourcePropertySource.getPropertyNames())
                .collect(toMap(
                        propertyName -> PROPERTY_NAME_PREFIX_PREFIX + propertyName,
                        resourcePropertySource::getProperty));
        return new MapPropertySource(resourcePropertySource.getName(), map);
    }
}
