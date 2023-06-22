package hibernate.h2.config;

import hibernate.h2.config.WorkWithDb;
import hibernate.h2.config.entity.Cashier;
import hibernate.h2.config.entity.Shift;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

class JavaConfigTest {
    @Test
    void javaConfig() {
        var configuration = getConfiguration();
        WorkWithDb.workWithDb(configuration);
    }

    private static Configuration getConfiguration() {
        var configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:");
        configuration.setProperty("hibernate.connection.username", "");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.connection.pool_size", "1");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.autocommit", "false");
        configuration.addAnnotatedClass(Cashier.class);
        configuration.addAnnotatedClass(Shift.class);
        return configuration;
    }
}