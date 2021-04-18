package hibernate.hql.select;

import hibernate.context.session.HibernateSessionFactory436;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class HqlSelect {

    @Test
    public void main() {
        RegionEntity region = new RegionEntity("Вологодская область");
        final long vologdaPopulation = 300000L;
        final String vologdaName = "Вологда";
        CityEntity city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        CityEntity city2 = new CityEntity("Санкт-Петербург", 4000000L, region);


        RegionEntity region2 = new RegionEntity("Московская область"
        );
        long moscowPopulation = 12000000L;
        CityEntity city3 = new CityEntity("Москва", moscowPopulation, region2);

        HibernateSessionFactory436 factory436 = HibernateSessionFactory436
                .makeFactory(RegionEntity.class, CityEntity.class);
        Session session = factory436.openSession();
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

        factory436.close();
    }
}