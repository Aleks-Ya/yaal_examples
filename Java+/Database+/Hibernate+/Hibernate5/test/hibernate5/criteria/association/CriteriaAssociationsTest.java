package hibernate5.criteria.association;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CriteriaAssociationsTest {

    private static void printList(List<?> result) {
        for (var obj : result) {
            System.out.println(obj);
        }
    }

    @Test
    void test() {
        var region = new RegionEntity("Вологодская область");
        final var vologdaPopulation = 300000L;
        final var vologdaName = "Вологда";
        var city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        var spbPopulation = 4000000L;
        var city2 = new CityEntity("Санкт-Петербург", spbPopulation, region);
        var city3 = new CityEntity("Волгоград", 300000L, region);


        var region2 = new RegionEntity("Московская область"
        );
        var moscowPopulation = 12000000L;
        var city4 = new CityEntity("Москва", moscowPopulation, region2);

        var session = HibernateSessionFactory5.makeFactory(RegionEntity.class, CityEntity.class).openSession();
        session.beginTransaction();
        session.save(region);
        session.save(region2);
        session.save(city1);
        session.save(city2);
        session.save(city3);
        session.save(city4);
        session.flush();
        session.getTransaction().commit();

        {
            System.out.println("\n ASSOCIATION:");
            var criteria = session.createCriteria(CityEntity.class);
            var regionCriteria = criteria.createCriteria("region");
            regionCriteria.add(Restrictions.ilike("name", "Вологод%"));
            criteria.addOrder(Order.desc("population"));
            printList(criteria.list());
        }

    }

}