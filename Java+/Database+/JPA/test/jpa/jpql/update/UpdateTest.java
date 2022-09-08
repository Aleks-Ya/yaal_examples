package jpa.jpql.update;

import jpa.jpql.MealEntities;
import org.junit.jupiter.api.Test;

import static jpa.JpaHelper.withEntityManagerAndSavedEntities;
import static org.assertj.core.api.Assertions.assertThat;

class UpdateTest {
    @Test
    void updateWhere() {
        withEntityManagerAndSavedEntities((em) -> {
            var transaction = em.getTransaction();
            transaction.begin();
            var query = em.createQuery("UPDATE MealEntity m SET name='Burger' WHERE name='Sandwich'");
            var updated = query.executeUpdate();
            transaction.commit();
            assertThat(updated).isEqualTo(1);
        }, MealEntities.entities);
    }
}