import entity.CityEntity;
import entity.RegionEntity;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.spi.PersistenceUnitTransactionType;
import java.util.List;

public class Main {

    private static EntityManager em;
    private static EntityManagerFactory emFactory;

    public static void main(String[] args) {
        initEntityManager();
        saveEntities();
        {
            System.out.println("\nSelect from one table (result list):");
            CriteriaBuilder builder = emFactory.getCriteriaBuilder();//1ый способ получения CriteriaBuilder
            CriteriaQuery<CityEntity> cq = builder.createQuery(CityEntity.class);
            Root<CityEntity> cityRoot = cq.from(CityEntity.class);
            cq.select(cityRoot);
            TypedQuery<CityEntity> tq = em.createQuery(cq);
            List<CityEntity> result = tq.getResultList();
            for (CityEntity city : result) {
                System.out.println(city);
            }
        }
        {
            System.out.println("\nSelect from one table (single result):");
            CriteriaBuilder builder = em.getCriteriaBuilder();//2ой способ получения CriteriaBuilder
            CriteriaQuery<CityEntity> cq = builder.createQuery(CityEntity.class);
            Root<CityEntity> cityRoot = cq.from(CityEntity.class);
            cq.select(cityRoot);
            TypedQuery<CityEntity> tq = em.createQuery(cq);
            tq.setMaxResults(1);
            CityEntity result = tq.getSingleResult();
            System.out.println(result);
        }
        em.close();
        System.exit(0);//Почему приложение не завершается?
    }

    private static void saveEntities() {
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
            em.getTransaction().begin();
            em.persist(region);
            em.persist(region2);
            em.persist(city1);
            em.persist(city2);
            em.persist(city3);
            em.persist(city4);
            em.flush();
            em.getTransaction().commit();
        }
    }

    private static void initEntityManager() {
        Configuration configuration = getConfiguration();

        StandardServiceRegistryBuilder srBuilder = new StandardServiceRegistryBuilder();
        srBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = srBuilder.build();

        emFactory = new EntityManagerFactoryImpl(
                PersistenceUnitTransactionType.RESOURCE_LOCAL, true, null, configuration, serviceRegistry, null);

        em = emFactory.createEntityManager();
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