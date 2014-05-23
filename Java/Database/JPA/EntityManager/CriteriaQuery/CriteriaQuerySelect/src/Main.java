import entity.CityEntity;
import entity.RegionEntity;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.spi.PersistenceUnitTransactionType;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RegionEntity region = new RegionEntity("Вологодская область");
        final long vologdaPopulation = 300000L;
        final String vologdaName = "Вологда";
        CityEntity city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        long spbPopulation = 4000000L;
        CityEntity city2 = new CityEntity("Санкт-Петербург", spbPopulation, region);
        CityEntity city3 = new CityEntity("Волгоград", 1000000L, region);


        RegionEntity region2 = new RegionEntity("Московская область");
        long moscowPopulation = 12000000L;
        CityEntity city4 = new CityEntity("Москва", moscowPopulation, region2);

        {
            EntityManager em = getEntityManager();
            em.persist(region);
            em.persist(region2);
            em.persist(city1);
            em.persist(city2);
            em.persist(city3);
            em.persist(city4);
            em.flush();
            em.close();
        }
        {
            EntityManager em = getEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<CityEntity> query = builder.createQuery(CityEntity.class);
        }
    }

    private static void printList(List result) {
        for (Object obj : result) {
            System.out.println(obj);
        }
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
        configuration.addAnnotatedClass(CityEntity.class);
        return configuration;
    }
}