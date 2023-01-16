package hibernate5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.ReflectionUtil;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Init SessionFactory of Hibernate 5.
 */
public class HibernateSessionFactory5 implements Closeable {
    private final SessionFactory sessionFactory;
    private final List<Session> sessions = new ArrayList<>();
    private final ImplicitNamingStrategy implicitNamingStrategy;
    private final PhysicalNamingStrategy physicalNamingStrategy;

    private HibernateSessionFactory5(Consumer<Configuration> configurationCustomizer) {
        var configuration = getConfiguration(configurationCustomizer);
        configurationCustomizer.accept(configuration);
        var serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        implicitNamingStrategy = ReflectionUtil.readFieldValue(configuration, "implicitNamingStrategy");
        this.physicalNamingStrategy = ReflectionUtil.readFieldValue(configuration, "physicalNamingStrategy");
    }

    public static HibernateSessionFactory5 makeFactory(Consumer<Configuration> configurationCustomizer) {
        return new HibernateSessionFactory5(configurationCustomizer);
    }

    public static HibernateSessionFactory5 makeFactory(Class<?>... classes) {
        return new HibernateSessionFactory5((configuration) -> {
            for (var clazz : classes) {
                configuration.addAnnotatedClass(clazz);
            }
        });
    }

    private static Configuration getConfiguration(Consumer<Configuration> configurationCustomizer) {
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
        configurationCustomizer.accept(configuration);
        return configuration;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session openSession() {
        var session = sessionFactory.openSession();
        sessions.add(session);
        return session;
    }

    @Override
    public void close() {
        for (var session : sessions) {
            session.close();
        }
        sessionFactory.close();
    }

    public ImplicitNamingStrategy getImplicitNamingStrategy() {
        return implicitNamingStrategy;
    }

    public PhysicalNamingStrategy getPhysicalNamingStrategy() {
        return physicalNamingStrategy;
    }
}