package hibernate4.log;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;

class LogTest {

    private static Configuration getConfiguration() {
        var configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:~/test");
        configuration.setProperty("hibernate.connection.username", "");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.connection.pool_size", "1");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.autocommit", "false");
        configuration.addAnnotatedClass(Cashier.class);
        return configuration;
    }

    @Test
    void main() {
        var configuration = getConfiguration();

        var serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        var sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        var session = sessionFactory.openSession();

        var t = session.beginTransaction();
        var mary = new Cashier("Mary", "Noise", 25);
        session.save(mary);
        t.commit();
        session.flush();
        session.close();
        sessionFactory.close();
    }
}