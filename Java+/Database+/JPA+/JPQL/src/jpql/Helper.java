package jpql;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import java.util.function.Consumer;

public class Helper {
    public static final UserEntity user = new UserEntity(1L, "Smith");
    public static final MealEntity meal1 = new MealEntity(1L, "Sandwich", MealCategory.FAST_FOOD, user);
    public static final MealEntity meal2 = new MealEntity(2L, "Soup", MealCategory.SOUPS, user);
    public static final MealEntity meal3 = new MealEntity(3L, "Salad", MealCategory.VEGETABLES, user);

    private static SessionFactory initEntityManagerFactory() {
        var configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", "jdbc:h2:~/test")
                .setProperty("hibernate.connection.pool_size", "1")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.connection.autocommit", "false")
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(MealEntity.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void saveEntities(EntityManager em) {
        em.getTransaction().begin();
        em.merge(user);
        em.merge(meal1);
        em.merge(meal2);
        em.merge(meal3);
        em.flush();
        em.getTransaction().commit();
    }

    public static void withEntityManager(Consumer<EntityManager> withEntityManager) {
        try (var emFactory = initEntityManagerFactory()) {
            withEntityManager.accept(emFactory.createEntityManager());
        }
    }

    public static void withEntityManagerAndSavedEntities(Consumer<EntityManager> withEntityManager) {
        withEntityManager((em) -> {
            saveEntities(em);
            withEntityManager.accept(em);
        });
    }

}