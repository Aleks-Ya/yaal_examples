import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Примеры операции DELETE на JPQL.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:context.xml")
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