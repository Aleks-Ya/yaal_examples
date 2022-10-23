package hibernate5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Init SessionFactory of Hibernate 5.
 */
public class HibernateSessionFactory5 implements Closeable {
    private final SessionFactory sessionFactory;
    private final List<Session> sessions = new ArrayList<>();

    private HibernateSessionFactory5(Map<String, String> propertiesOverride,
                                     PhysicalNamingStrategy physicalNamingStrategy,
                                     Class<?>... classes) {
        var configuration = getConfiguration(propertiesOverride, physicalNamingStrategy, classes);
        var serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static HibernateSessionFactory5 makeFactory(Map<String, String> propertiesOverride,
                                                       PhysicalNamingStrategy physicalNamingStrategy,
                                                       Class<?>... classes) {
        return new HibernateSessionFactory5(propertiesOverride, physicalNamingStrategy, classes);
    }

    public static HibernateSessionFactory5 makeFactory(Class<?>... classes) {
        return new HibernateSessionFactory5(Map.of(), null, classes);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session openSession() {
        final var session = sessionFactory.openSession();
        sessions.add(session);
        return session;
    }

    private Configuration getConfiguration(Map<String, String> propertiesOverride,
                                           PhysicalNamingStrategy physicalNamingStrategy,
                                           Class<?>... classes) {
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
        if (physicalNamingStrategy != null) {
            configuration.setPhysicalNamingStrategy(physicalNamingStrategy);
        }
        propertiesOverride.forEach(configuration::setProperty);
        for (var clazz : classes) {
            configuration.addAnnotatedClass(clazz);
        }
        return configuration;
    }

    @Override
    public void close() {
        for (var session : sessions) {
            session.close();
        }
        sessionFactory.close();
    }
}