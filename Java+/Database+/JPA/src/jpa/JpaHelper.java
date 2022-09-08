package jpa;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class JpaHelper {

    private static SessionFactory initEntityManagerFactory(List<Class<?>> entityClasses, String url) {
        var configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.pool_size", "5")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.connection.autocommit", "false");
        entityClasses.forEach(configuration::addAnnotatedClass);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void saveEntities(EntityManager em, List<?> entities) {
        em.getTransaction().begin();
        entities.forEach(em::merge);
        em.flush();
        em.getTransaction().commit();
    }

    public static void withEntityManagerFactory(Consumer<EntityManagerFactory> withEntityManagerFactory,
                                                List<Class<?>> entityClasses) {
        try (var h2Server = H2Server.newServer();
             var emFactory = initEntityManagerFactory(entityClasses, h2Server.newDatabaseUrl())) {
            withEntityManagerFactory.accept(emFactory);
        }
    }

    public static void withEntityManager(Consumer<EntityManager> withEntityManager, List<Class<?>> entityClasses) {
        withEntityManagerFactory((emFactory) -> withEntityManager.accept(emFactory.createEntityManager()), entityClasses);
    }

    public static void withEntityManagerAndSavedEntities(Consumer<EntityManager> withEntityManager, List<?> entities) {
        var entityClasses = entities.stream().map(Object::getClass).distinct()
                .collect(Collectors.toCollection(() -> new ArrayList<Class<?>>()));
        withEntityManager((em) -> {
            saveEntities(em, entities);
            withEntityManager.accept(em);
        }, entityClasses);
    }
}
