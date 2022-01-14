import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;

public class JavaOnly {

    public static void main(String[] args) {
        var em = getEntityManager();
        var region = new RegionEntity("Вологодская область");
        em.persist(region);
        System.out.println(em.contains(region));
    }

    private static EntityManager getEntityManager() {
        var configuration = getConfiguration();

        var srBuilder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = srBuilder.build();

        var emFactory = configuration.buildSessionFactory(serviceRegistry);

        return emFactory.createEntityManager();
    }

    private static Configuration getConfiguration() {
        var configuration = new Configuration();
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