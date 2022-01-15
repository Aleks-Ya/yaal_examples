package jpql.delete;

import org.junit.jupiter.api.Test;

import static jpql.Helper.withEntityManagerAndSavedEntities;
import static org.assertj.core.api.Assertions.assertThat;

class JpqlDelete {

    @Test
    void deleteAll() {
        withEntityManagerAndSavedEntities((em) -> {
            var transaction = em.getTransaction();
            transaction.begin();
            var query = em.createQuery("DELETE FROM MealEntity");
            var updated = query.executeUpdate();
            transaction.commit();
            assertThat(updated).isEqualTo(3);
        });
    }

    @Test
    void deleteWhere() {
        withEntityManagerAndSavedEntities((em) -> {
            var transaction = em.getTransaction();
            transaction.begin();
            var query = em.createQuery("DELETE FROM MealEntity WHERE name='Sandwich'");
            var updated = query.executeUpdate();
            transaction.commit();
            assertThat(updated).isEqualTo(1);
        });
    }

    @Test
    void deleteJoin() {
        withEntityManagerAndSavedEntities((em) -> {
            var transaction = em.getTransaction();
            transaction.begin();
            var query = em.createQuery(
                    "DELETE FROM MealEntity WHERE user IN(FROM UserEntity WHERE name='Smith')");
            var updated = query.executeUpdate();
            transaction.commit();
            assertThat(updated).isEqualTo(3);
        });
    }
}