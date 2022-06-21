package context.property.configuration_properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties("information.city-info")
record CityOnRecord(String title, int year, URI uri, URI previousUri) {
}
