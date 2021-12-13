package hibernate5.context.filtering.dynamic;

import hibernate5.context.session.HibernateSessionFactory5;
import org.junit.jupiter.api.Test;

import static hibernate5.context.filtering.dynamic.CityEntity.CITY_POPULATION_FILTER;
import static hibernate5.context.filtering.dynamic.CityEntity.MIN_POPULATION_PARAM;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use {@link org.hibernate.annotations.Filter} annotation for dynamic filtering.
 */
class FilterTest {

    @Test
    void filter() {
        var moscowName = "Moscow";
        var spbName = "Saint-Petersburg";
        var sochiName = "Sochi";
        var vologdaName = "Vologda";

        try (var factory436 = HibernateSessionFactory5.makeFactory(CityEntity.class)) {
            var session = factory436.openSession();
            session.beginTransaction();
            session.save(new CityEntity(moscowName, 25_000_000));
            session.save(new CityEntity(spbName, 10_000_000));
            session.save(new CityEntity(sochiName, 700_000));
            session.save(new CityEntity(vologdaName, 300_000));
            session.flush();
            session.getTransaction().commit();

            {
                session.enableFilter(CITY_POPULATION_FILTER).setParameter(MIN_POPULATION_PARAM, 1_000_000);
                var actCities = session
                        .createQuery("from CityEntity", CityEntity.class)
                        .getResultList();
                session.disableFilter(CITY_POPULATION_FILTER);
                assertThat(actCities).extracting("name").containsOnly(moscowName, spbName);
            }
            {
                session.enableFilter(CITY_POPULATION_FILTER).setParameter(MIN_POPULATION_PARAM, 500_000);
                var actCities = session
                        .createQuery("from CityEntity", CityEntity.class)
                        .getResultList();
                session.disableFilter(CITY_POPULATION_FILTER);
                assertThat(actCities).extracting("name").containsOnly(moscowName, spbName, sochiName);
            }
            {
                var actCities = session
                        .createQuery("from CityEntity", CityEntity.class)
                        .getResultList();
                assertThat(actCities).extracting("name")
                        .containsOnly(moscowName, spbName, sochiName, vologdaName);
            }

        }
    }
}