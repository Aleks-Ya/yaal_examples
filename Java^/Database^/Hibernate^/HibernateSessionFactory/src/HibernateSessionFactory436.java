import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class HibernateSessionFactory436 implements Closeable {
    private SessionFactory sessionFactory;
    private List<Session> sessions = new ArrayList<>();

    private HibernateSessionFactory436(Class... classes) {
        Configuration configuration = getConfiguration(classes);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static HibernateSessionFactory436 makeFactory(Class... classes) {
        return new HibernateSessionFactory436(classes);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session openSession() {
        final Session session = sessionFactory.openSession();
        sessions.add(session);
        return session;
    }

    private Configuration getConfiguration(Class... classes) {
        Configuration configuration = new Configuration();
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
        for (Class clazz : classes) {
            configuration.addAnnotatedClass(clazz);
        }
        return configuration;
    }

    @Override
    public void close() {
        for (Session session : sessions) {
            session.close();
        }
        sessionFactory.close();
    }
}