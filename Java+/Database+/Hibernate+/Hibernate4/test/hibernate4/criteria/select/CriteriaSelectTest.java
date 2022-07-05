package hibernate4.criteria.select;

import hibernate4.context.session.HibernateSessionFactory436;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CriteriaSelectTest {

    private static void printList(List result) {
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
        var city3 = new CityEntity("Волгоград", 1000000L, region);


        var region2 = new RegionEntity("Московская область"
        );
        var moscowPopulation = 12000000L;
        var city4 = new CityEntity("Москва", moscowPopulation, region2);

        var session = HibernateSessionFactory436.makeFactory(RegionEntity.class, CityEntity.class).openSession();
        session.save(region);
        session.save(region2);
        session.save(city1);
        session.save(city2);
        session.save(city3);
        session.save(city4);
        session.flush();

        {
            System.out.println("\nSELECT всех объектов класса CityEntity:");
            var criteria = session.createCriteria(CityEntity.class);
            printList(criteria.list());
            //todo Добавить CityEntity extends PlaceEntity - должны находиться объекты обоих классов
        }
        {
            System.out.println("\nSELECT + equals CRITERION:");
            var criteria = session.createCriteria(CityEntity.class);
            Criterion criterion = Restrictions.eq("name", vologdaName);
            criteria.add(criterion);
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + like case sensitive CRITERION:");
            var criteria = session.createCriteria(CityEntity.class);
            criteria.add(Restrictions.like("name", "Вол", MatchMode.START));
            criteria.add(Restrictions.like("name", "Вол%"));// тоже самое
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + like case INsensitive CRITERION:");
            var criteria = session.createCriteria(CityEntity.class);
            criteria.add(Restrictions.ilike("name", "вол", MatchMode.START));
            criteria.add(Restrictions.ilike("name", "вол%"));// тоже самое
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + and CRITERION:");
            var criteria = session.createCriteria(CityEntity.class);
            criteria.add(Restrictions.like("name", vologdaName, MatchMode.START));
            criteria.add(Restrictions.eq("population", vologdaPopulation));
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + or 2 arguments CRITERION:");
            var criteria = session.createCriteria(CityEntity.class);
            Criterion name = Restrictions.like("name", vologdaName, MatchMode.START);
            Criterion population = Restrictions.eq("population", moscowPopulation);
            var or = Restrictions.or(name, population);
            criteria.add(or);
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + or >2 arguments CRITERION:");
            var criteria = session.createCriteria(CityEntity.class);
            Criterion name = Restrictions.like("name", vologdaName, MatchMode.START);
            Criterion population = Restrictions.eq("population", moscowPopulation);
            Criterion population2 = Restrictions.eq("population", spbPopulation);
            var or = Restrictions.disjunction();
            or.add(name);
            or.add(population);
            or.add(population2);
            criteria.add(or);
            printList(criteria.list());
        }
        {
            System.out.println("\nSELECT + sql CRITERION:");
            var criteria = session.createCriteria(CityEntity.class);
            criteria.add(Restrictions.sqlRestriction("{alias}.name like 'Вол%'"));
            printList(criteria.list());
        }
    }
}