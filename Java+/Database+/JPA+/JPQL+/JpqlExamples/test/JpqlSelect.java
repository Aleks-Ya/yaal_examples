import domain.MealEntity;
import domain.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Примеры операции SELECT JPQL.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:context.xml")
public class JpqlSelect {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void selectAll() {
        var query = em.createQuery("SELECT m FROM MealEntity m", MealEntity.class);
        var meals = query.getResultList();
        assertEquals(3, meals.size());
    }

    @Test
    public void selectAllShort() {
        var query = em.createQuery("FROM UserEntity", UserEntity.class);
        var users = query.getResultList();
        assertEquals(1, users.size());
    }

    @Test
    public void fromWhere() {
        var query = em.createQuery("FROM MealEntity WHERE name LIKE 'Sandwich'", MealEntity.class);
        var meal = query.getSingleResult();
        assertEquals("Sandwich", meal.getName());
    }

    @Test
    public void joinByPath() {
        var query = em.createQuery("FROM MealEntity WHERE user.name LIKE 'Smith'", MealEntity.class);
        var meals = query.getResultList();
        assertEquals(3, meals.size());
        assertEquals("Smith", meals.get(0).getUser().getName());
    }

    @Test
    public void joinByJoin() {
        var query = em.createQuery("SELECT m FROM MealEntity m JOIN m.user u WHERE u.name LIKE 'Smith'", MealEntity.class);
        var meals = query.getResultList();
        assertEquals(3, meals.size());
        assertEquals("Smith", meals.get(0).getUser().getName());
    }
}