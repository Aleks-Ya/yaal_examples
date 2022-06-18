package property.source.custom;

import org.springframework.core.env.PropertySource;
import org.springframework.lang.NonNull;

import java.util.Map;

class MapPropertySource extends PropertySource<Object> {
    private final Map<String, Object> properties;

    public MapPropertySource(String name, Map<String, Object> properties) {
        super(name);
        this.properties = properties;
    }

    @Override
    public Object getProperty(@NonNull String name) {
        return properties.get(name);
    }
}
