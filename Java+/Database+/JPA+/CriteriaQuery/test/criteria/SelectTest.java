package criteria;

import org.junit.jupiter.api.Test;

import static criteria.Helper.initEntityManagerFactory;
import static criteria.Helper.saveEntities;

class SelectTest {

    @Test
    void select() {
        try (var emFactory = initEntityManagerFactory()) {
            var em = emFactory.createEntityManager();
            saveEntities(em);
            var builder = em.getCriteriaBuilder();
            var cq = builder.createQuery(CityEntity.class);
            var cityRoot = cq.from(CityEntity.class);
            cq.select(cityRoot);
            var tq = em.createQuery(cq);
            tq.setMaxResults(1);
            var result = tq.getSingleResult();
            System.out.println(result);
            em.close();
        }
    }

}