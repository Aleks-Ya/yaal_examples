import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitTransactionType;

public class JavaOnly {

    public static void main(String[] args) {
        EntityManager em = getEntityManager();
        RegionEntity region = new RegionEntity("Вологодская область");
        em.persist(region);
        System.out.println(em.contains(region));
    }

    private static EntityManager getEntityManager() {
        Configuration configuration = getConfiguration();

        StandardServiceRegistryBuilder srBuilder = new StandardServiceRegistryBuilder();
        srBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = srBuilder.build();

        EntityManagerFactory emFactory = new EntityManagerFactoryImpl(
                PersistenceUnitTransactionType.RESOURCE_LOCAL, true, null, configuration, serviceRegistry, null);

        return emFactory.createEntityManager();
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:~/test");
        configuration.setProperty("hibernate.connection.pool_size", "1");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.autocommit", "false");
        configuration.addAnnotatedClass(RegionEntity.class);
        return configuration;
    }
}