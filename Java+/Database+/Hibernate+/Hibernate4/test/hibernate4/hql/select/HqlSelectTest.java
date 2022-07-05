package hibernate4.hql.select;

import hibernate4.context.session.HibernateSessionFactory436;
import org.hibernate.Query;
import org.junit.jupiter.api.Test;

class HqlSelectTest {

    @Test
    void main() {
        var region = new RegionEntity("Вологодская область");
        final var vologdaPopulation = 300000L;
        final var vologdaName = "Вологда";
        var city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        var city2 = new CityEntity("Санкт-Петербург", 4000000L, region);


        var region2 = new RegionEntity("Московская область"
        );
        var moscowPopulation = 12000000L;
        var city3 = new CityEntity("Москва", moscowPopulation, region2);

        var factory436 = HibernateSessionFactory436
                .makeFactory(RegionEntity.class, CityEntity.class);
        var session = factory436.openSession();
        session.save(region);
        session.save(region2);
        session.save(city1);
        session.save(city2);
        session.save(city3);
        session.flush();

        {
            System.out.println("\nSELECT:");
            Query query = session.createQuery("from CityEntity");
            for (var obj : query.list()) {
                System.out.println(obj);
            }
        }

        {
            System.out.println("\nSELECT + WHERE:");
            Query query = session.createQuery("from CityEntity c where c.name=:city or c.population=:population");
            query.setParameter("city", vologdaName);
            query.setParameter("population", moscowPopulation);
            for (var obj : query.list()) {
                System.out.println(obj);
            }
        }

        {
            System.out.println("\nSELECT + JOIN:");
            Query query = session.createQuery("from CityEntity as c inner join c.region");
            for (var obj : query.list()) {
                System.out.println(obj);
            }
        }

        factory436.close();
    }
}