import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        var query = em.createQuery("DELETE FROM MealEntity");
        var updated = query.executeUpdate();
        assertEquals(3, updated);
    }

    @Test
    public void deleteWhere() {
        var query = em.createQuery("DELETE FROM MealEntity WHERE name='Sandwich'");
        var updated = query.executeUpdate();
        assertEquals(1, updated);
    }

    @Test
    public void deleteJoin() {
        var query = em.createQuery(
                "DELETE FROM MealEntity WHERE user IN(FROM UserEntity WHERE name='Smith')");
        var updated = query.executeUpdate();
        assertEquals(3, updated);
    }
}