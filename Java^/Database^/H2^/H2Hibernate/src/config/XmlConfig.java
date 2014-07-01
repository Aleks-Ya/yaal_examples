package config;

import org.hibernate.cfg.Configuration;

/**
 * Конфигурирование Hibernate в XML.
 */
public class XmlConfig extends AbstractConfig {

    protected Configuration getConfiguration() {
        return new Configuration().configure();
    }
}