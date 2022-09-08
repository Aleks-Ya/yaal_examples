package jpa.criteria;

import org.junit.jupiter.api.Test;

import static jpa.JpaHelper.saveEntities;
import static jpa.JpaHelper.withEntityManagerAndSavedEntities;
import static jpa.JpaHelper.withEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Get instance of {@link javax.persistence.criteria.CriteriaBuilder}.
 */
class InstantiateCriteriaBuilderTest {

    @Test
    void fromEntityManager() {
        withEntityManagerAndSavedEntities((em) -> {
            var builder = em.getCriteriaBuilder();
            var cq = builder.createQuery(CityEntity.class);
            var result = em.createQuery(cq.select(cq.from(CityEntity.class))).getResultList();
            em.close();
            assertThat(result).contains(RegionEntities.city1, RegionEntities.city2, RegionEntities.city3, RegionEntities.city4);
        }, RegionEntities.entities);
    }

    @Test
    void fromEntityManagerFactory() {
        withEntityManagerFactory((emFactory) -> {
            var em = emFactory.createEntityManager();
            saveEntities(em, RegionEntities.entities);
            var builder = emFactory.getCriteriaBuilder();
            var cq = builder.createQuery(CityEntity.class);
            var result = em.createQuery(cq.select(cq.from(CityEntity.class))).getResultList();
            em.close();
            assertThat(result).contains(RegionEntities.city1, RegionEntities.city2, RegionEntities.city3, RegionEntities.city4);
        }, RegionEntities.entityClasses);
    }

}