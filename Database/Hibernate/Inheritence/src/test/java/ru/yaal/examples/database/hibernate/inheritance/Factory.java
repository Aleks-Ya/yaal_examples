package ru.yaal.examples.database.hibernate.inheritance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import ru.yaal.examples.database.hibernate.inheritance.joined.CarJoined;
import ru.yaal.examples.database.hibernate.inheritance.joined.TransportJoined;
import ru.yaal.examples.database.hibernate.inheritance.joined.discriminator.CarJoinedDiscriminator;
import ru.yaal.examples.database.hibernate.inheritance.joined.discriminator.TransportJoinedDiscriminator;
import ru.yaal.examples.database.hibernate.inheritance.single.CarSingle;
import ru.yaal.examples.database.hibernate.inheritance.single.TransportSingle;
import ru.yaal.examples.database.hibernate.inheritance.tableperclass.CarTablePerClass;
import ru.yaal.examples.database.hibernate.inheritance.tableperclass.TransportTablePerClass;

import java.util.Properties;

public class Factory {
    private static SessionFactory sessionFactory;

    private Factory() {
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
        configuration.addAnnotatedClass(TransportSingle.class);
        configuration.addAnnotatedClass(TransportJoined.class);
        configuration.addAnnotatedClass(TransportTablePerClass.class);
        configuration.addAnnotatedClass(TransportJoinedDiscriminator.class);
        configuration.addAnnotatedClass(CarSingle.class);
        configuration.addAnnotatedClass(CarJoined.class);
        configuration.addAnnotatedClass(CarTablePerClass.class);
        configuration.addAnnotatedClass(CarJoinedDiscriminator.class);
    }

    public static <T> void save(T obj) throws Exception {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(obj);
        session.getTransaction().commit();
        session.flush();
    }

}
