package jpa.native_query;

import jpa.jpql.MealEntities;
import jpa.jpql.MealEntity;
import jpa.jpql.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.withEntityManager;
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

    @Test
    void insert() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var query = em.createNativeQuery("INSERT INTO UserEntity(id, name) VALUES (5, 'Mark')", UserEntity.class);
            var changed = query.executeUpdate();
            assertThat(changed).isEqualTo(1);
            var users = em.createNativeQuery("SELECT * FROM UserEntity", UserEntity.class).getResultList();
            assertThat(users).contains(new UserEntity(5L, "Mark"));
            em.getTransaction().commit();
        }, List.of(UserEntity.class));
    }
}