import domain.MealEntity;
import domain.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Примеры операции SELECT JPQL.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JpqlSelect {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void selectAll() {
        TypedQuery<MealEntity> query = em.createQuery("SELECT m FROM MealEntity m", MealEntity.class);
        List<MealEntity> meals = query.getResultList();
        assertEquals(3, meals.size());
    }

    @Test
    public void selectAllShort() {
        TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity", UserEntity.class);
        List<UserEntity> users = query.getResultList();
        assertEquals(1, users.size());
    }

    @Test
    public void fromWhere() {
        TypedQuery<MealEntity> query = em.createQuery("FROM MealEntity WHERE name LIKE 'Sandwich'", MealEntity.class);
        MealEntity meal = query.getSingleResult();
        assertEquals("Sandwich", meal.getName());
    }

    @Test
    public void joinByPath() {
        TypedQuery<MealEntity> query = em.createQuery("FROM MealEntity WHERE user.name LIKE 'Smith'", MealEntity.class);
        List<MealEntity> meals = query.getResultList();
        assertEquals(3, meals.size());
        assertEquals("Smith", meals.get(0).getUser().getName());
    }

    @Test
    public void joinByJoin() {
        TypedQuery<MealEntity> query = em.createQuery("SELECT m FROM MealEntity m JOIN m.user u WHERE u.name LIKE 'Smith'", MealEntity.class);
        List<MealEntity> meals = query.getResultList();
        assertEquals(3, meals.size());
        assertEquals("Smith", meals.get(0).getUser().getName());
    }
}