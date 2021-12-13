package hibernate5.context.filtering.statik;

import hibernate5.context.session.HibernateSessionFactory5;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use {@link org.hibernate.annotations.Where} annotation for static filtering.
 */
class WhereTest {

    @Test
    void where() {
        var moscowName = "Moscow";
        var spbName = "Saint-Petersburg";
        var city1 = new CityEntity(moscowName, 25_000_000);
        var city2 = new CityEntity(spbName, 10_000_000);
        var city3 = new CityEntity("Sochi", 700_000);
        var city4 = new CityEntity("Vologda", 300_000);

        try (var factory436 = HibernateSessionFactory5.makeFactory(CityEntity.class)) {
            var session = factory436.openSession();
            session.beginTransaction();
            session.save(city1);
            session.save(city2);
            session.save(city3);
            session.save(city4);
            session.flush();
            session.getTransaction().commit();

            var actCities = session.createQuery("from CityEntity", CityEntity.class).list();
            assertThat(actCities).extracting("name").containsOnly(moscowName, spbName);
        }
    }
}