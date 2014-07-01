import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Configuration configuration = getConfiguration();

        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        Session session = sessionFactory.openSession();
        CashierThread th1 = new CashierThread("CashierThread #1", session);
        CashierThread th2 = new CashierThread("CashierThread #2", session);

        th1.beginTransaction();
        TimeUnit.MILLISECONDS.sleep(100);
        th2.beginTransaction();
        TimeUnit.MILLISECONDS.sleep(100);
        th2.commitTransaction();
        TimeUnit.MILLISECONDS.sleep(100);
        th1.commitTransaction();
    }

    private static Configuration getConfiguration() {
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
        configuration.addAnnotatedClass(Cashier.class);
        return configuration;
    }
}