package hibernate5.hql.select;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

class HqlSelect {

    @Test
    void test() {
        var region = new RegionEntity("Вологодская область");
        final var vologdaPopulation = 300000L;
        final var vologdaName = "Вологда";
        var city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        var city2 = new CityEntity("Санкт-Петербург", 4000000L, region);


        var region2 = new RegionEntity("Московская область");
        var moscowPopulation = 12000000L;
        var city3 = new CityEntity("Москва", moscowPopulation, region2);

        var factory436 = HibernateSessionFactory5.makeFactory(RegionEntity.class, CityEntity.class);
        var session = factory436.openSession();
        session.beginTransaction();
        session.save(region);
        session.save(region2);
        session.save(city1);
        session.save(city2);
        session.save(city3);
        session.flush();
        session.getTransaction().commit();

        {
            System.out.println("\nSELECT:");
            Query<CityEntity> query = session.createQuery("from CityEntity", CityEntity.class);
            for (var obj : query.list()) {
                System.out.println(obj);
            }
        }

        {
            System.out.println("\nSELECT + WHERE:");
            Query<CityEntity> query = session.createQuery("from CityEntity c where c.name=:city or c.population=:population",
                    CityEntity.class);
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