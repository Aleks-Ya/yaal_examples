package context.property.configuration_properties.on_record.configuration_roperties_can;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties("information.city-info")
record CityRecord(String title, int year, URI uri, URI previousUri) {
}
