import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.Properties;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {
    }

    public static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory == null) {
            sessionFactory = makeSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory makeSessionFactory() throws Exception {
        Configuration cfg = new Configuration();
        addClasses(cfg);
        cfg.setProperties(getHibernateProperties());
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(cfg.getProperties());
        return cfg.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
    }

    private static Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/hibernate_examples");
        properties.put("hibernate.connection.username", "postgres");
        properties.put("hibernate.connection.password", "postgres");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
        properties.put("hibernate.current_session_context_class", "thread");
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "create");
        return properties;
    }

    private static void addClasses(Configuration configuration) {
        configuration.addAnnotatedClass(People.class);
        configuration.addAnnotatedClass(Address.class);
    }
}
