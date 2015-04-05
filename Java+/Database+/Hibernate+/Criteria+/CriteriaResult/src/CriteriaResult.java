import entity.CityEntity;
import entity.RegionEntity;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;

public class CriteriaResult {
    public static void main(String[] args) {
        RegionEntity region = new RegionEntity("Вологодская область");
        final long vologdaPopulation = 300000L;
        final String vologdaName = "Вологда";
        CityEntity city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        long spbPopulation = 4000000L;
        CityEntity city2 = new CityEntity("Санкт-Петербург", spbPopulation, region);
        CityEntity city3 = new CityEntity("Волгоград", 300000L, region);


        RegionEntity region2 = new RegionEntity("Московская область"
        );
        long moscowPopulation = 12000000L;
        CityEntity city4 = new CityEntity("Москва", moscowPopulation, region2);

        Session session = getSession();
        session.save(region);
        session.save(region2);
        session.save(city1);
        session.save(city2);
        session.save(city3);
        session.save(city4);
        session.flush();

        {
            System.out.println("\n PAGING:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            criteria.setFirstResult(1);//нумерация начинается с 0
            criteria.setMaxResults(2);
            List result = criteria.list();

            printList(result);
        }
        {
            System.out.println("\n UNIQUE RESULT:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            criteria.setMaxResults(1);
            System.out.println(criteria.uniqueResult());
        }
        {
            System.out.println("\n NonUniqueResultException:");
            try {
                Criteria criteria = session.createCriteria(CityEntity.class);
                criteria.uniqueResult();
            } catch (NonUniqueResultException e) {
                System.out.println(e.getMessage());
            }
        }
        {
            System.out.println("\n ORDER:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            criteria.addOrder(Order.desc("population"));
            criteria.addOrder(Order.asc("name"));
            List result = criteria.list();

            printList(result);
        }

    }

    private static void printList(List result) {
        for (Object obj : result) {
            System.out.println(obj);
        }
    }

    private static Session getSession() {
        Configuration configuration = getConfiguration();

        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();

        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory.openSession();
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