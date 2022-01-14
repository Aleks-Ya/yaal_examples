package criteria;

import org.junit.jupiter.api.Test;

import static criteria.Helper.initEntityManagerFactory;
import static criteria.Helper.saveEntities;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Get instance of {@link javax.persistence.criteria.CriteriaBuilder}.
 */
class InstantiateCriteriaBuilderTest {

    @Test
    void fromEntityManager() {
        try (var emFactory = initEntityManagerFactory()) {
            var em = emFactory.createEntityManager();
            saveEntities(em);
            var builder = em.getCriteriaBuilder();
            var cq = builder.createQuery(CityEntity.class);
            var result = em.createQuery(cq.select(cq.from(CityEntity.class))).getResultList();
            em.close();
            assertThat(result).contains(Helper.city1, Helper.city2, Helper.city3, Helper.city4);
        }
    }

    @Test
    void fromEntityManagerFactory() {
        try (var emFactory = initEntityManagerFactory()) {
            var em = emFactory.createEntityManager();
            saveEntities(em);
            var builder = emFactory.getCriteriaBuilder();
            var cq = builder.createQuery(CityEntity.class);
            var result = em.createQuery(cq.select(cq.from(CityEntity.class))).getResultList();
            em.close();
            assertThat(result).contains(Helper.city1, Helper.city2, Helper.city3, Helper.city4);
        }
    }

}