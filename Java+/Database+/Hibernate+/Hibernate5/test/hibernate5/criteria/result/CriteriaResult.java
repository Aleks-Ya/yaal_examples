package hibernate5.criteria.result;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CriteriaResult {

    @Test
    public void main() {
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

}