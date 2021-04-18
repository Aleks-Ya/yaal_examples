package hibernate.criteria.select;

import hibernate.context.session.HibernateSessionFactory5;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CriteriaSelect {

    @Test
    public void main() {
        RegionEntity region = new RegionEntity("Вологодская область");
        final long vologdaPopulation = 300000L;
        final String vologdaName = "Вологда";
        CityEntity city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        long spbPopulation = 4000000L;
        CityEntity city2 = new CityEntity("Санкт-Петербург", spbPopulation, region);
        CityEntity city3 = new CityEntity("Волгоград", 1000000L, region);


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
            System.out.println("\nSELECT всех объектов класса CityEntity:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            printList(criteria.list());
            //todo Добавить CityEntity extends PlaceEntity - должны находиться объекты обоих классов
        }
        {
            System.out.println("\nSELECT + equals CRITERION:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            Criterion criterion = Restrictions.eq("name", vologdaName);
            criteria.add(criterion);
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + like case sensitive CRITERION:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            criteria.add(Restrictions.like("name", "Вол", MatchMode.START));
            criteria.add(Restrictions.like("name", "Вол%"));// тоже самое
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + like case INsensitive CRITERION:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            criteria.add(Restrictions.ilike("name", "вол", MatchMode.START));
            criteria.add(Restrictions.ilike("name", "вол%"));// тоже самое
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + and CRITERION:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            criteria.add(Restrictions.like("name", vologdaName, MatchMode.START));
            criteria.add(Restrictions.eq("population", vologdaPopulation));
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + or 2 arguments CRITERION:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            Criterion name = Restrictions.like("name", vologdaName, MatchMode.START);
            Criterion population = Restrictions.eq("population", moscowPopulation);
            LogicalExpression or = Restrictions.or(name, population);
            criteria.add(or);
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + or >2 arguments CRITERION:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            Criterion name = Restrictions.like("name", vologdaName, MatchMode.START);
            Criterion population = Restrictions.eq("population", moscowPopulation);
            Criterion population2 = Restrictions.eq("population", spbPopulation);
            Disjunction or = Restrictions.disjunction();
            or.add(name);
            or.add(population);
            or.add(population2);
            criteria.add(or);
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + sql CRITERION:");
            Criteria criteria = session.createCriteria(CityEntity.class);
            criteria.add(Restrictions.sqlRestriction("{alias}.name like 'Вол%'"));
            printList(criteria.list());
        }
    }

    private static void printList(List result) {
        for (Object obj : result) {
            System.out.println(obj);
        }
    }

}