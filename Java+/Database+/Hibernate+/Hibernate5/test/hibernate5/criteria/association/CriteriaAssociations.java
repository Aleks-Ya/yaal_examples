package hibernate5.criteria.association;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CriteriaAssociations {

    @Test
    public void test() {
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

        Session session = HibernateSessionFactory5.makeFactory(RegionEntity.class, CityEntity.class).openSession();
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

}