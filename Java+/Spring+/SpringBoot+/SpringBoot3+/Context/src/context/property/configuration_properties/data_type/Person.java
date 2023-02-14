package context.property.configuration_properties.data_type;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.List;
import java.util.Map;

@ConfigurationProperties("info.person")
record Person(String name, int age, Gender gender, URI webSite, Map<AnimalType, List<String>> animals) {
}
