package criteria;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;

public class Helper {
    public static final RegionEntity region1 = new RegionEntity(1L, "Вологодская область");
    public static final CityEntity city1 = new CityEntity(1L, "Вологда", 300000L, region1);
    public static final CityEntity city2 = new CityEntity(2L, "Санкт-Петербург", 4000000L, region1);
    public static final CityEntity city3 = new CityEntity(3L, "Волгоград", 1000000L, region1);
    public static final RegionEntity region2 = new RegionEntity(2L, "Московская область");
    public static final CityEntity city4 = new CityEntity(4L, "Москва", 12000000L, region2);

    public static SessionFactory initEntityManagerFactory() {
        var configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", "jdbc:h2:~/test")
                .setProperty("hibernate.connection.pool_size", "1")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.connection.autocommit", "false")
                .addAnnotatedClass(RegionEntity.class)
                .addAnnotatedClass(CityEntity.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void saveEntities(EntityManager em) {
        em.getTransaction().begin();
        em.merge(region1);
        em.merge(region2);
        em.merge(city1);
        em.merge(city2);
        em.merge(city3);
        em.merge(city4);
        em.flush();
        em.getTransaction().commit();
    }
}
