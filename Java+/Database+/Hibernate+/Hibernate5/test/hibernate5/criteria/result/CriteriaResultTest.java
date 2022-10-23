package hibernate5.criteria.result;

import hibernate5.HibernateSessionFactory5;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

class CriteriaResultTest {

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

        var region2 = new RegionEntity("Московская область");
        var moscowPopulation = 12000000L;
        var city4 = new CityEntity("Москва", moscowPopulation, region2);

        try (var sessionFactory = HibernateSessionFactory5.makeFactory(RegionEntity.class, CityEntity.class);
             var session = sessionFactory.openSession()) {
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
                System.out.println("\n PAGING:");
                var criteria = session.createCriteria(CityEntity.class);
                criteria.setFirstResult(1);//нумерация начинается с 0
                criteria.setMaxResults(2);
                var result = criteria.list();

                printList(result);
            }
            {
                System.out.println("\n UNIQUE RESULT:");
                var criteria = session.createCriteria(CityEntity.class);
                criteria.setMaxResults(1);
                System.out.println(criteria.uniqueResult());
            }
            {
                System.out.println("\n NonUniqueResultException:");
                try {
                    var criteria = session.createCriteria(CityEntity.class);
                    criteria.uniqueResult();
                } catch (NonUniqueResultException e) {
                    System.out.println(e.getMessage());
                }
            }
            {
                System.out.println("\n ORDER:");
                var criteria = session.createCriteria(CityEntity.class);
                criteria.addOrder(Order.desc("population"));
                criteria.addOrder(Order.asc("name"));
                var result = criteria.list();

                printList(result);
            }
        }
    }

}