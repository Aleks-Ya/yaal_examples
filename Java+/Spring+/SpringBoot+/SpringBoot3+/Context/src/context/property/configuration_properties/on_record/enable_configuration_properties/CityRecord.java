package context.property.configuration_properties.on_record.enable_configuration_properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties("information.city-info")
record CityRecord(String title, int year, URI uri, URI previousUri) {
}
