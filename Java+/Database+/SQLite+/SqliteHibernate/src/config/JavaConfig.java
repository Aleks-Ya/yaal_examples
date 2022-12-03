package config;

import entity.Cashier;
import entity.Shift;
import org.hibernate.cfg.Configuration;

/**
 * Конфигурирование Hibernate в java-коде.
 */
public class JavaConfig extends AbstractConfig {
    protected Configuration getConfiguration() {
        var configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
        configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:/tmp/test.sqlite");
        configuration.setProperty("hibernate.connection.username", "");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.connection.pool_size", "1");
        configuration.setProperty("hibernate.dialect", "org.sqlite.hibernate.dialect.SQLiteDialect");
        configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.autocommit", "false");
        configuration.addAnnotatedClass(Cashier.class);
        configuration.addAnnotatedClass(Shift.class);
        return configuration;
    }
}