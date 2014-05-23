import entity.CityEntity;
import entity.RegionEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {
        RegionEntity region = new RegionEntity("Вологодская область");
        final long vologdaPopulation = 300000L;
        final String vologdaName = "Вологда";
        CityEntity city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        CityEntity city2 = new CityEntity("Санкт-Петербург", 4000000L, region);


        RegionEntity region2 = new RegionEntity("Московская область"
        );
        long moscowPopulation = 12000000L;
        CityEntity city3 = new CityEntity("Москва", moscowPopulation, region2);

        Session session = getSession();
        session.save(region);
        session.save(region2);
        session.save(city1);
        session.save(city2);
        session.save(city3);
        session.flush();

        {
            System.out.println("\nSELECT:");
            Query query = session.createQuery("from CityEntity");
            for (Object obj : query.list()) {
                System.out.println(obj);
            }
        }

        {
            System.out.println("\nSELECT + WHERE:");
            Query query = session.createQuery("from CityEntity c where c.name=:city or c.population=:population");
            query.setParameter("city", vologdaName);
            query.setParameter("population", moscowPopulation);
            for (Object obj : query.list()) {
                System.out.println(obj);
            }
        }

        {
            System.out.println("\nSELECT + JOIN:");
            Query query = session.createQuery("from CityEntity as c inner join c.region");
            for (Object obj : query.list()) {
                System.out.println(obj);
            }
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