package ru.yaal.examples.database.hibernate.bidirectassosiation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = null;
        try {
            Main main = new Main();
            sessionFactory = main.getSessionFactory();
            Session session = sessionFactory.openSession();

            Payment payment = new Payment("Bike buy");

            Transaction transaction = new Transaction(payment);

            Slip slipA = new Slip(transaction, "С Вас 1 000 $$$");
            Slip slipB = new Slip(transaction, "С Вас $10 000 000");

            session.beginTransaction();
            session.save(payment);
            session.save(transaction);
            session.save(slipA);
            session.save(slipB);
            session.getTransaction().commit();
            session.flush();

        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    public SessionFactory getSessionFactory() throws Exception {
        Configuration cfg = new Configuration();
        addClasses(cfg);
        cfg.setProperties(getHibernateProperties());
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(cfg.getProperties());
        return cfg.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
    }

    private Properties getHibernateProperties() {
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
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

    private void addClasses(Configuration configuration) {
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(Transaction.class);
        configuration.addAnnotatedClass(Slip.class);
    }
}
