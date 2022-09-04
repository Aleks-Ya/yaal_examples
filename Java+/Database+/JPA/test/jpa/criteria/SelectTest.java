package jpa.criteria;

import org.junit.jupiter.api.Test;

import static jpa.JpaHelper.withEntityManagerAndSavedEntities;

class SelectTest {
    @Test
    void select() {
        withEntityManagerAndSavedEntities((em) -> {
            var builder = em.getCriteriaBuilder();
            var cq = builder.createQuery(CityEntity.class);
            var cityRoot = cq.from(CityEntity.class);
            cq.select(cityRoot);
            var tq = em.createQuery(cq);
            tq.setMaxResults(1);
            var result = tq.getSingleResult();
            System.out.println(result);
            em.close();
        }, RegionEntities.entities);
    }
}