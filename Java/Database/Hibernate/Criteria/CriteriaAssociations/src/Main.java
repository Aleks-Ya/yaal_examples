import entity.CityEntity;
import entity.RegionEntity;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;

public class Main {
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
            System.out.println("\n ASSOCIATION:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            Criteria regionCriteria = criteria.createCriteria("region");
            regionCriteria.add(Restrictions.ilike("name", "Вологод%"));
            criteria.addOrder(Order.desc("population"));
            printList(criteria.list());
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