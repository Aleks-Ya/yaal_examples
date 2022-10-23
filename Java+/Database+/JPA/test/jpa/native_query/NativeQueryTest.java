package jpa.native_query;

import jpa.jpql.MealEntities;
import jpa.jpql.MealEntity;
import org.junit.jupiter.api.Test;

import static jpa.JpaHelper.withEntityManagerAndSavedEntities;
import static org.assertj.core.api.Assertions.assertThat;

class NativeQueryTest {
    @Test
    void selectAll() {
        withEntityManagerAndSavedEntities((em) -> {
            var meals = em.createNativeQuery("SELECT * FROM MealEntity", MealEntity.class).getResultList();
            assertThat(meals).contains(MealEntities.meal1, MealEntities.meal2, MealEntities.meal3);
        }, MealEntities.entities);
    }
}