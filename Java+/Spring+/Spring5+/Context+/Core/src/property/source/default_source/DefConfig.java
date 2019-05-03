package property.source.default_source;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Можно задать путь к файлу свойств через свойства JVM: -Dconf=file:${user.dir}/any/file.properties
 * Если свойство "conf" не задано, будет использовано значение по-умолчанию.
 */
@Configuration
@PropertySource("${conf:file:${user.dir}/resourcesTest/property/source/default_source/def.properties}")
class DefConfig {
}
