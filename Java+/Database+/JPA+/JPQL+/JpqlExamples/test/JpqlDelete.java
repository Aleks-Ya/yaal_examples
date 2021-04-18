import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Примеры операции DELETE на JPQL.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class JpqlDelete {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void deleteAll() {
        Query query = em.createQuery("DELETE FROM MealEntity");
        int updated = query.executeUpdate();
        assertEquals(3, updated);
    }

    @Test
    public void deleteWhere() {
        Query query = em.createQuery("DELETE FROM MealEntity WHERE name='Sandwich'");
        int updated = query.executeUpdate();
        assertEquals(1, updated);
    }

    @Test
    public void deleteJoin() {
        Query query = em.createQuery(
                "DELETE FROM MealEntity WHERE user IN(FROM UserEntity WHERE name='Smith')");
        int updated = query.executeUpdate();
        assertEquals(3, updated);
    }
}