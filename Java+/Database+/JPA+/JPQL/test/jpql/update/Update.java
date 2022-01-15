package jpql.update;

import org.junit.jupiter.api.Test;

import static jpql.Helper.withEntityManagerAndSavedEntities;
import static org.assertj.core.api.Assertions.assertThat;

class Update {

    @Test
    void updateWhere() {
        withEntityManagerAndSavedEntities((em) -> {
            var transaction = em.getTransaction();
            transaction.begin();
            var query = em.createQuery("UPDATE MealEntity m SET name='Burger' WHERE name='Sandwich'");
            var updated = query.executeUpdate();
            transaction.commit();
            assertThat(updated).isEqualTo(1);
        });
    }

}